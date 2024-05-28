package result;

import model.GameData;

import java.util.Collection;

public record ListGameResult(String message, Collection<GameData> games) {
}
