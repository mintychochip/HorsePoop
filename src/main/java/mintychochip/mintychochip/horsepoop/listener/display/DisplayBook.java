package mintychochip.mintychochip.horsepoop.listener.display;

import java.util.ArrayList;
import java.util.List;
import mintychochip.mintychochip.horsepoop.api.TraitEnum;
import mintychochip.mintychochip.horsepoop.container.BaseTrait;
import mintychochip.mintychochip.horsepoop.listener.book.Page;
import mintychochip.mintychochip.horsepoop.listener.book.TablePage;
import mintychochip.mintychochip.horsepoop.listener.book.TitlePage;
import mintychochip.mintychochip.horsepoop.listener.book.TraitPage;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;

public class DisplayBook {
  private static int MAX_LINES = 13;
  private final List<Page> pages;
  private final String title;
  private final String author;
  private final boolean withoutTitlePage;
  private DisplayBook(PageBuilder pageBuilder, String title, String author, boolean withoutTitlePage) {
    this.pages = pageBuilder.pages;
    this.title = title;
    this.author = author;
    this.withoutTitlePage = withoutTitlePage;
  }
  public static class PageBuilder {
    private final List<Page> pages = new ArrayList<>();
    public PageBuilder addTitlePage(EntityType entityType) {
      pages.add(new TitlePage(entityType));
      return this;
    }
    public PageBuilder addTableOfContentsPage() {
      pages.add(new TablePage());
      return this;
    }

    public <U extends TraitEnum> PageBuilder addTraitPages(String header, List<BaseTrait<U>> traits) {
      TablePage tablePage = (TablePage) this.findTablePage();
      for (List<BaseTrait<U>> list : this.splitTraitList(traits, MAX_LINES)) {
        pages.add(new TraitPage<>(header, pages.size(), list));
        if(tablePage != null) {
          tablePage.addTraitButton(pages.size(),header);
        }
      }

      return this;
    }

    private Page findTablePage() {
      return pages.stream().filter(page -> page instanceof TablePage).findFirst().orElse(null);
    }
    private <U extends TraitEnum> List<List<BaseTrait<U>>> splitTraitList(List<BaseTrait<U>> mainList, int size) {
      List<List<BaseTrait<U>>> results = new ArrayList<>();
      if(mainList.size() > size) {
        for(int i = 0; i < mainList.size(); i+= size) {
          results.add(mainList.subList(i, Math.min(i + size, mainList.size())));
        }
      } else {
        results.add(mainList);
      }
      return results;
    }
    public DisplayBook create(String title, String author, boolean withoutTitlePage) {
      return new DisplayBook(this,title,author, withoutTitlePage);
    }
  }

  public Book getBook() {
    List<Page> list = pages;
    if (withoutTitlePage) {
      list = list.stream().filter(page -> !(page instanceof TitlePage)).toList();
    }
    return Book.book(Component.text(title), Component.text(author),
        list.stream().map(Page::getContent).toList());
  }
}
