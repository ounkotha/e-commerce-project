package service;
import org.asha.springboot.ApiSpringbootApplication;
import org.asha.springboot.entity.Product;
import org.asha.springboot.repository.ProductRepository;
import org.asha.springboot.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ApiSpringbootApplication.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // mock initialize
        product = new Product(1L, "Laptop", "High-end laptop", 1200.0);
    }

    @Test
    void testSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product saved = productService.saveProduct(product);

        assertNotNull(saved);
        assertEquals("Laptop", saved.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product found = productService.getProductById(1L);

        assertNotNull(found);
        assertEquals("Laptop", found.getName());
    }

    @Test
    void testUpdateProduct() {
        Product updatedInfo = new Product(1L, "Gaming Laptop", "RTX Enabled", 1500.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedInfo);

        Product updated = productService.updateProduct(1L, updatedInfo);

        assertEquals("Gaming Laptop", updated.getName());
        assertEquals(1500.0, updated.getPrice());
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}
