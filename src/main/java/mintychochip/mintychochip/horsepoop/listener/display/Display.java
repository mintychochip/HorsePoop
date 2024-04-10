package mintychochip.mintychochip.horsepoop.listener.display;

import net.kyori.adventure.text.Component;

public interface Display {

    HoverDisplay getHoverDisplay();
    Component getTextValue();
    void setHoverDisplay(HoverDisplay hoverDisplay);

}
