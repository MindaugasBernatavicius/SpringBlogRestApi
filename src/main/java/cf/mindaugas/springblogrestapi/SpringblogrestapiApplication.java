package cf.mindaugas.springblogrestapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.util.List;

@SpringBootApplication
public class SpringblogrestapiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringblogrestapiApplication.class, args);
    }
}

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
class ForwardingController {
    @RequestMapping("/{path:[^\\.]+}/**")
    public String forward() {
        return "forward:/";
    }
}

@RestController
@RequestMapping("/blogposts")
@CrossOrigin(origins = "*", maxAge = 3600)
class BlogPostsController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @GetMapping(value = "")
    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public BlogPost getBlogPostById(@PathVariable Long id) {
        return blogPostRepository.getOne(id);
        // TODO :: 404 if not found
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        blogPostRepository.delete(blogPostRepository.getOne(id));
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); // 204
    }
}

@Repository
interface BlogPostRepository extends JpaRepository<BlogPost, Long> { }

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blogposts")
class BlogPost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
}
