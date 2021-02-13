package cf.mindaugas.springblogrestapi;

import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class SpringblogrestapiApplication {

    List<BlogPost> blogPosts = new ArrayList<>() {{
        add(new BlogPost(1, "Bp1", "Text 1"));
        add(new BlogPost(2, "Bp2", "Text 2"));
        add(new BlogPost(3, "Bp3", "Text 3"));
        add(new BlogPost(4, "Bp4", "Text 4"));
    }};

    public static void main(String[] args) {
        SpringApplication.run(SpringblogrestapiApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "<h1>Hello World</h1>";
    }

    @GetMapping("/blogposts")
    public List<BlogPost> blogposts() {
        return blogPosts;
    }

    @DeleteMapping("/blogposts/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer id) {
        BlogPost itemToDelete =blogPosts.stream()
                .filter(blogPost -> blogPost.getId()
                .equals(id)).findAny().orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        blogPosts.remove(itemToDelete);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT); // 204
    }
}

@Data
@AllArgsConstructor
class BlogPost {
    private Integer id;
    private String title;
    private String text;
}
