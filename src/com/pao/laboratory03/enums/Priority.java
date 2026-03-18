package com.pao.laboratory03.enums;

public enum Priority
{
    LOW(1, "green")
            {
                public String getEmoji()
                {
                    return "\uD83D\uDFE2";
                }
            },
    MEDIUM(2, "yellow")
    {
        public String getEmoji()
        {
            return "\uD83D\uDFE1";
        }
    },
    HIGH(3, "orange")
            {
                public String getEmoji()
                {
                    return "\uD83D\uDFE0";
                }
            },
    CRITICAL(4, "red")
            {
                public String getEmoji()
                {
                    return "\uD83D\uDD34";
                }
            };

    private int level;
    private String color;

    private Priority(int level, String color)
    {
        this.level = level;
        this.color = color;
    }

    public int getLevel()
    {
        return level;
    }

    public String getColor()
    {
        return color;
    }

    public abstract String getEmoji();
}
