public interface LevelController {
    int getRows();
    int getCols();

    void startLevel();
    void onTileClick(TileButton tile);

    default boolean hasHint() { return false; }
    default void useHint() { /* hint yok */ }
}