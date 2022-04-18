package commons;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(DecreaseTimeJoker.class)
})

public abstract class JokerCard {


    private final String name;
    private String description;
    private final boolean onlyMultiplayer;

    public JokerCard(String name, String description, boolean onlyMultiplayer) {
        this.name = name;
        this.description = description;
        this.onlyMultiplayer = onlyMultiplayer;
    }

    public abstract void useCard();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOnlyMultiplayer() {
        return onlyMultiplayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JokerCard jokerCard = (JokerCard) o;
        return onlyMultiplayer == jokerCard.onlyMultiplayer && description.equals(jokerCard.description);
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, onlyMultiplayer);
    }

    @Override
    public String toString() {
        return "JokerCard{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", onlyMultiplayer=" + onlyMultiplayer +
                '}';
    }



}
