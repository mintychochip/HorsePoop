package mintychochip.mintychochip.horsepoop.listener.display;

import java.util.ArrayList;
import java.util.List;
import mintychochip.genesis.util.StringUtil;
import mintychochip.mintychochip.horsepoop.container.AnimalGenome;
import mintychochip.mintychochip.horsepoop.util.ImagePixelColorDecoder;
import net.kyori.adventure.text.Component;
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

//  public Component rarityComponent() {
//    Rarity rarity = animalGenome.getRarity();
//    return Component.text("Rarity: ")
//        .append(Component.text(rarity.getLegacyColor() + StringUtil.capitalizeFirstLetter(rarity.toPlainString().toLowerCase()))
//            .hoverEvent(HoverEvent.hoverEvent(Action.SHOW_TEXT, Component.text(
//                "Rarity determines how many possible mutations your animal can have."))));
//  }

//  public Component genesComponent() {
//    Component component = Component.text("").append(Component.newline());
//    for (Gene gene : animalGenome.getGenes()) {
//      component = component.append(this.individualGeneComponent(gene))
//          .append(Component.newline());
//    }
//    return component;
//  }
//  public Component characteristicComponent() {
//    Component component = Component.text("").append(Component.newline());
//    for (Characteristic characteristic : animalGenome.getCharacteristics()) {
//      component = component.append(this.individualCharacteristicComponent(characteristic))
//          .append(Component.newline());
//    }
//    return component;
//  }
//
//  private Component individualGeneComponent(Gene gene) {
//    GeneTrait geneTrait = traitFetcher.getGeneTrait(gene.getTrait());
//    return this.pieceComponent(StringUtil.capitalizeFirstLetter(geneTrait.getKey()) + ": ",
//        geneTrait.getShortDescription(),
//        traitFetcher.geneComponent(gene));
//  }
//
//  private Component pieceComponent(String traitKey, String description, Component value) {
//    return Component.text("")
//        .append(Component.text(traitKey)
//            .color(NamedTextColor.GOLD)
//            .hoverEvent(HoverEvent.hoverEvent(Action.SHOW_TEXT,
//                Component.text(description))))
//        .append(value);
//  }
//  private Component individualCharacteristicComponent(Characteristic characteristic) {
//    CharacteristicTrait characteristicTrait = traitFetcher.getCharacteristicTrait(
//        characteristic.getTrait());
//    return this.pieceComponent(StringUtil.capitalizeFirstLetter(characteristicTrait.getKey()) + ": ",
//        characteristicTrait.getShortDescription(),
//        traitFetcher.characteristicComponent(characteristic));
//  }
////  public Component genderComponent() {
////    Gender gender = animalGenome.getGender();
////    TextComponent text = Component.text(
////        StringUtil.capitalizeFirstLetter(gender.toString().toLowerCase()));
////    return Component.text("Gender: ").append(text.color(gender
////        .getTextColor()).hoverEvent(HoverEvent.hoverEvent(Action.SHOW_TEXT, text)));
////  }

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
