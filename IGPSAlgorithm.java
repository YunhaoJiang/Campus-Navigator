public interface IGPSAlgorithm extends GraphADT<IPlace> {
  /**
   * This method calculates the weight between two places.
   *
   * @param place1 The first place.
   * @param place2 The second place.
   * @return The weight between the two places.
   */
  public int calculateWeight(IPlace place1, IPlace place2) throws IllegalAccessException;

  public int calculateTime(IPlace start, IPlace end, int mapFactor);

  public void automaticInsertEdge(IPlace place);
}
