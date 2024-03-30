package mintychochip.mintychochip.horsepoop.listener.display;

import java.util.ArrayList;
import java.util.List;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.util.StringUtil;
import mintychochip.mintychochip.horsepoop.config.ConfigManager;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.Gene.GeneType;
import mintychochip.mintychochip.horsepoop.util.ImagePixelColorDecoder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEvent.Action;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class AnimalDisplayInfo {

  @NonNull
  private final EntityType entityType;
  @NonNull
  private final AnimalGenome animalGenome;
  @NonNull
  private final String name;

  public AnimalDisplayInfo(@NotNull EntityType entityType, @NotNull AnimalGenome animalGenome,
      @NonNull String name) {
    this.entityType = entityType;
    this.animalGenome = animalGenome;
    this.name = name;
  }

  public Component rarityComponent() {
    return Component.text("Rarity: ")
        .append(Component.text(animalGenome.getRarity().toString())
            .hoverEvent(HoverEvent.hoverEvent(Action.SHOW_TEXT, Component.text(
                "Rarity determines how many possible mutations your animal can have."))));
  }

  public Component genesComponent() {
    Component component = Component.text("")
        .append(Component.newline().append(Component.newline()));
    for (Gene gene : animalGenome.getGenes()) {
      component = component.append(this.individualGeneComponent(gene))
          .append(Component.newline());
    }
    return component;
  }

  private Component individualGeneComponent(Gene gene) {
    return Component.text("")
        .append(Component.text(StringUtil.capitalizeFirstLetter(gene.getTrait().getKey()) + ": ")
            .color(NamedTextColor.GOLD)
            .hoverEvent(HoverEvent.hoverEvent(Action.SHOW_TEXT,
                Component.text(gene.getTrait().getShortDescription()))))
        .append(Component.text(gene.toString()));
  }

  public Component genderComponent() {
    return Component.text("Gender: ")
        .append(animalGenome.getGender().getUnicode())
        .hoverEvent(HoverEvent.hoverEvent(Action.SHOW_TEXT, Component.text(
            StringUtil.capitalizeFirstLetter(
                animalGenome.getGender().toString().toLowerCase()))));
  }

  public Component entityTypeComponent() {
    return Component.text("Specimen: ").append(Component.text(
        StringUtil.capitalizeFirstLetter(entityType.toString().toLowerCase())));
  }

  public Component getEntityTypeComponentImage(int padding) {
    RGB[][] decode = ImagePixelColorDecoder.decode(
        "images\\" + entityType.toString().toLowerCase() + ".png");
    Component component = Component.text("");
    for (int x = 0; x < decode.length; x++) {
      List<RGB> rgbs = new ArrayList<>();
      for (int y = 0; y < decode.length; y++) {
        rgbs.add(decode[y][x]);
      }
      component = component.append(Component.newline()).append(buildLine(rgbs, padding));
    }
    return component;
  }

  private Component buildLine(List<RGB> rgbLine, int padding) {
    Component component = Component.text("");
    for (int i = 0; i < padding; i++) {
      component = component.append(Component.text(" "));
    }
    for (RGB rgb : rgbLine) {
      component = component.append(Component.text('█').color(TextColor.color(rgb)));
    }

    return component;
  }
}
