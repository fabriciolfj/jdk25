package org.example.reflection;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Map;

public class Post {
    private String title;
    private String author;
    private Metadata metadata;

    // Getters and Setters

    public static class Metadata {
        private int wordCount;

        @Override
        public String toString() {
            return "Metadata{" +
                    "wordCount=" + wordCount +
                    '}';
        }

        // Getters and Setters
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", metadata=" + metadata +
                '}';
    }

    static void main() throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        var post = new Post();
        BeanInfo beanInfo = Introspector.getBeanInfo(Post.class);
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            System.out.println(pd.getName());
        }

        var titlePd = new PropertyDescriptor("title", Post.class);

        Method write = titlePd.getWriteMethod();
        Method read = titlePd.getReadMethod();

        if (write != null) {
            write.invoke(post, "Reflections in Java");
        }
        if (read != null) {
            String value = (String) read.invoke(post);
            System.out.println(value);
        }

        BeanUtils.setProperty(post, "title", "Commons BeanUtils Rocks");
        String title = BeanUtils.getProperty(post, "title");

        Map<String, Object> data = Map.of("title", "Map → Bean", "author", "Baeldung Team");
        BeanUtils.populate(post, data);

        IO.println(post);

        Post source = new Post();
        source.setTitle("Source");
        source.setAuthor("Alice");

        Post target = new Post();
        BeanUtils.copyProperties(target, source);

        //ConvertUtils.register(new LocalDateConverter(), LocalDate.class);

        if (post.getMetadata() == null) {
            post.setMetadata(new Post.Metadata());
        }
        BeanUtils.setProperty(post, "metadata.wordCount", 850); //entendi caminhos dentro da classe

        IO.println(post);
    }
}