public class CharacterFactory {
    public static CharacterCls getCharacter(String profession, String name, int xp, int level) {
        return switch (profession) {
            case "Warrior" -> new Warrior(name, xp, level);
            case "Rogue" -> new Rogue(name, xp, level);
            case "Mage" -> new Mage(name, xp, level);
            default -> null;
        };
    }
}
