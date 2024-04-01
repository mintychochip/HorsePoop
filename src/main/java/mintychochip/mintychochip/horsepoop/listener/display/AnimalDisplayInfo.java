package mintychochip.mintychochip.horsepoop.listener.display;

import java.util.ArrayList;
import java.util.List;
import mintychochip.genesis.util.StringUtil;
import mintychochip.mintychochip.horsepoop.HorsePoop;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.container.Gene;
import mintychochip.mintychochip.horsepoop.container.GeneTrait;
import mintychochip.mintychochip.horsepoop.container.TraitFetcher;
import mintychochip.mintychochip.horsepoop.util.ImagePixelColorDecoder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEvent.Action;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class AnimalDisplayInfo {
  private final TraitFetcher traitFetcher = new TraitFetcher(HorsePoop.GSON); //will fix later
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

//  public Component rarityComponent() {
//    Rarity rarity = animalGenome.getRarity();
//    return Component.text("Rarity: ")
//        .append(Component.text(rarity.getLegacyColor() + StringUtil.capitalizeFirstLetter(rarity.toPlainString().toLowerCase()))
//            .hoverEvent(HoverEvent.hoverEvent(Action.SHOW_TEXT, Component.text(
//                "Rarity determines how many possible mutations your animal can have."))));
//  }

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
    GeneTrait geneTrait = traitFetcher.getGeneTrait(gene.getTrait());
    return Component.text("")
        .append(Component.text(StringUtil.capitalizeFirstLetter(geneTrait.getKey()) + ": ")
            .color(NamedTextColor.GOLD)
            .hoverEvent(HoverEvent.hoverEvent(Action.SHOW_TEXT,
                Component.text(geneTrait.getShortDescription()))))
        .append(Component.text(gene.toString(traitFetcher)));
  }

//  public Component genderComponent() {
//    Gender gender = animalGenome.getGender();
//    TextComponent text = Component.text(
//        StringUtil.capitalizeFirstLetter(gender.toString().toLowerCase()));
//    return Component.text("Gender: ").append(text.color(gender
//        .getTextColor()).hoverEvent(HoverEvent.hoverEvent(Action.SHOW_TEXT, text)));
//  }

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
