package test_code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(AppConfig.class)
class ProductDaoTest {
    @Autowired
    private ProductDao dao;

    @BeforeEach
    void setUp() {
        dao.deleteAll();
    }

    private Product newProduct(String id, String name, int price) {
        Product product = new Product();

        product.setId(id);
        product.setName(name);
        product.setPrice(price);

        return product;
    }

    @Test
    void add() {
        assertEquals(0, dao.getCount());
        dao.add(newProduct("test", "kimchi", 10000));
        assertEquals(1, dao.getCount());
    }

    @Test
    void get() {
        dao.add(newProduct("test", "kimchi", 10000));
        Product product = dao.get("test");
        assertEquals("kimchi", product.getName());
        assertEquals(10000, product.getPrice());
    }

    @Test
    void add_중복_id_예외() {
        dao.add(newProduct("test", "kimchi", 10000));
        assertThrows(IllegalStateException.class, () -> dao.add(newProduct("test", "kimchi", 10000)));
    }

    @Test
    void get_없는_id_예외() {
        assertThrows(NoSuchElementException.class, () -> dao.get("blabla"));
    }

    @Disabled
    @Test
    void 일부러_실패하는_테스트() {
        dao.add(newProduct("test", "kimchi", 10000));
        assertEquals(2, dao.getCount());
    }
}