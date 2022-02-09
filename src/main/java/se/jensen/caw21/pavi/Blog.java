package se.jensen.caw21.pavi;

import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import java.io.StringWriter;
import java.io.Writer;

public class Blog implements Jsonable {
    public String title;
    public int id;
    public String description;
    public String author;

    enum keys implements JsonKey {
        ID("id"),
        TITLE("title"),
        DESCRIPTION("description"),
        AUTHOR("author");


        private final Object value;

        keys(final Object value) {
            this.value = value;
        }

        @Override
        public String getKey() {
            return this.name().toLowerCase();
        }

        @Override
        public Object getValue() {
            return this.value;
        }

    }

    public Blog() {
    }

    public Blog(String title, String description, String author) {
        this.title=title;
        this.description=description;
        this.author=author;

    }

    public Blog(int id,String title,String description,String author) {
        this.id=id;
        this.title=title;
        this.description=description;
        this.author=author;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            this.toJson(writable);
        } catch (final Exception e) {
        }
        return writable.toString();
    }

    @Override
    public void toJson(final Writer writable) {
        try {
            final JsonObject json = new JsonObject();
            json.put(keys.TITLE.getKey(), this.getTitle());
            json.put(keys.DESCRIPTION.getKey(), this.getDescription());
            json.put(keys.ID.getKey(), this.getId());
            json.put(keys.AUTHOR.getKey(),this.getAuthor());
            json.toJson(writable);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public String toString() {
        return "Blog{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

