package mintychochip.mintychochip.horsepoop.container;

public interface Crosser<T extends Characteristic> {
    BaseTrait<T> crossTraits(BaseTrait<T> father, BaseTrait<T> mother);
}
