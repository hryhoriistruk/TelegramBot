package ua.javarush;

public enum ServiceCategory {
    IT_PROGRAMMING("IT та програмування"),
    DESIGN_CREATIVE("Дизайн та креатив"),
    TEXTS_TRANSLATIONS("Тексти та переклади"),
    AUDIO_VIDEO("Аудіо та відео"),
    BUSINESS_CONSULTING("Бізнес та консалтинг");

    private final String displayName;

    ServiceCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ServiceCategory fromString(String text) {
        for (ServiceCategory category : ServiceCategory.values()) {
            if (category.displayName.equalsIgnoreCase(text)) {
                return category;
            }
        }
        return null;
    }
}