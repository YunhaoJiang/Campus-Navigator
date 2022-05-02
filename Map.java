public class Map extends CS400Graph<IPlace> implements IGPSAlgorithm {

  /**
   * This method calculates the weight between two places.
   *
   * @param place1 The first place.
   * @param place2 The second place.
   * @return The weight between the two places.
   */
  @Override
  public int calculateWeight(IPlace place1, IPlace place2) {
    if (place1 == null || place2 == null) {
      throw new IllegalArgumentException("Cannot calculate weight between two null place");
    }

    int x1 = place1.getX();
    int y1 = place1.getY();
    int x2 = place2.getX();
    int y2 = place2.getY();

    //calculate the direct distance between the two places
    return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
  }

  @Override
  public int calculateTime(IPlace start, IPlace end, int mapFactor) {
    return this.getPathCost(start,end) / mapFactor;
  }

  @Override
  public void automaticInsertEdge(IPlace place) {
    this.insertVertex(place);
    Vertex toConnect = vertices.get(place);
    for (Vertex currentVertex : vertices.values()) {
      if (toConnect.equals(currentVertex)) {
        continue;// skip the same vertex
      }
      if (!checkIntersection(currentVertex, toConnect)) {
        currentVertex.edgesLeaving.add(
            new Edge(toConnect, calculateWeight(place, currentVertex.data)));
        System.out.println("Connecting: " + currentVertex.data.getName() + " and " + toConnect.data.getName());
      }

    }

  }

  private boolean checkIntersection(Vertex origin, Vertex target) {
    for (Vertex currentVertex : vertices.values()) {
      if (currentVertex.equals(origin) || currentVertex.equals(target)) {
        continue; // skip current
      }
      for (Edge currentEdge : currentVertex.edgesLeaving) {
        IPlace existOrigin = currentVertex.data;
        IPlace existTarget = currentEdge.target.data;

        IPlace possibleOrigin = origin.data;
        IPlace possibleTarget = target.data;

        double existSlope =
            calculateSlope(existOrigin.getX(), existOrigin.getY(), existTarget.getX(),
                existTarget.getY());
        double possibleSlope =
            calculateSlope(possibleOrigin.getX(), possibleOrigin.getY(), possibleTarget.getX(),
                possibleTarget.getY());
        if (existSlope != possibleSlope) {
          double existYIntercept =
              calculateYIntercept(existSlope, existOrigin.getX(), existOrigin.getY());
          double possibleYIntercept =
              calculateYIntercept(possibleSlope, possibleOrigin.getX(), possibleOrigin.getY());

          double xIntersection =
              (possibleYIntercept - existYIntercept) / (existSlope - possibleSlope);
          double yIntersection = existSlope * xIntersection + existYIntercept;

          if (xIntersection > Math.max(Math.min(existOrigin.getX(), existTarget.getX()),
              Math.min(possibleOrigin.getX(), possibleTarget.getX())) && xIntersection < Math.min(
              Math.max(existOrigin.getX(), existTarget.getX()),
              Math.max(possibleOrigin.getX(), possibleTarget.getX()))) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private double calculateSlope(int x1, int y1, int x2, int y2) {
    return (double) (y2 - y1) / (x2 - x1);
  }

  private double calculateYIntercept(double slope, int x, int y) {
    // calculate Y Intercept
    return y - slope * x;
  }
}
