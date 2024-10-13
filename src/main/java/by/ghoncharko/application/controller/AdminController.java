package by.ghoncharko.application.controller;


import by.ghoncharko.application.entity.Category;
import by.ghoncharko.application.entity.Product;
import by.ghoncharko.application.entity.Warehouse;
import by.ghoncharko.application.repository.*;
import by.ghoncharko.application.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AuditLogRepository auditLogRepository;
    private final BackupRepository backupRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryTransactionRepostiry inventoryTransactionRepostiry;
    private final ProductRepository productRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final WarehouseRepository warehouseRepository;

    @GetMapping("/home")
    public String homePage() {
        return "ahome";
    }

    @GetMapping("/addLight")
    public String addLight(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("warehouses", warehouseRepository.findAll());
        model.addAttribute("pageTitle", "Добавление Товара");
        return "admin/addLight";
    }
    @PostMapping("/addLight")
    public String addLightSubmit(Product product, RedirectAttributes redirectAttributes) {
        try {
            productRepository.save(product);
            redirectAttributes.addFlashAttribute("successMessage", "Товар успешно добавлен!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении товара: " + e.getMessage());
        }
        return "redirect:/admin/addLight";
    }
    @GetMapping("/editLight")
    public String editLight(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin/editLight";
    }
    @GetMapping("/editLight/{id}")
    public String editLightForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("warehouses", warehouseRepository.findAll());
            model.addAttribute("pageTitle", "Редактирование Товара");
            return "admin/editLightForm";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Товар не найден.");
            return "redirect:/admin/editLight";
        }
    }
    @PostMapping("/editLight/{id}")
    @Transactional
    public String editLightSubmit(@PathVariable("id") Long id, @ModelAttribute Product updatedProduct, RedirectAttributes redirectAttributes) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product existingProduct = productOpt.get();
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setQuantityInStock(updatedProduct.getQuantityInStock());
            existingProduct.setUnitPrice(updatedProduct.getUnitPrice());
            existingProduct.setWarehouse(updatedProduct.getWarehouse());

            try {
                productRepository.save(existingProduct);
                redirectAttributes.addFlashAttribute("successMessage", "Товар успешно обновлен!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении товара: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Товар не найден.");
        }
        return "redirect:/admin/editLight";
    }
    @GetMapping("/deleteLight/{id}")
    public String deleteLight(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            productRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Товар успешно удален!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при удалении товара: " + e.getMessage());
        }
        return "redirect:/admin/editLight";
    }
    @GetMapping("/addCategory")
    public String addCategory(Model model) {
        model.addAttribute("pageTitle", "Добавление Категории");
        return "admin/addCategory"; // Возвращаем страницу с формой добавления категории
    }

    @PostMapping("/addCategory")
    public String addCategorySubmit(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        try {
            categoryRepository.save(category);
            redirectAttributes.addFlashAttribute("successMessage", "Категория успешно добавлена!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении категории: " + e.getMessage());
        }
        return "redirect:/admin/addCategory";
    }

    @GetMapping("/editCategory")
    public String editCategory(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/editCategory"; // Возвращаем страницу для редактирования категорий
    }

    @GetMapping("/editCategory/{id}")
    public String editCategoryForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            model.addAttribute("category", categoryOpt.get());
            model.addAttribute("pageTitle", "Редактирование Категории");
            return "admin/editCategoryForm"; // Возвращаем форму редактирования категории
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Категория не найдена.");
            return "redirect:/admin/editCategory";
        }
    }

    @PostMapping("/editCategory/{id}")
    public String editCategorySubmit(@PathVariable("id") Long id, @ModelAttribute Category updatedCategory, RedirectAttributes redirectAttributes) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            Category existingCategory = categoryOpt.get();
            existingCategory.setCategoryName(updatedCategory.getCategoryName());

            try {
                categoryRepository.save(existingCategory);
                redirectAttributes.addFlashAttribute("successMessage", "Категория успешно обновлена!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении категории: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Категория не найдена.");
        }
        return "redirect:/admin/editCategory";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Категория успешно удалена!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при удалении категории: " + e.getMessage());
        }
        return "redirect:/admin/editCategory";
    }
    @GetMapping("/addWarehouse")
    public String addWarehouse(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        model.addAttribute("pageTitle", "Создание склада");
        return "admin/addWarehouse"; // Возвращаем страницу с формой добавления склада
    }
    @PostMapping("/addWarehouse")
    public String addWarehouseSubmit(@ModelAttribute Warehouse warehouse, RedirectAttributes redirectAttributes) {
        try {
            warehouseRepository.save(warehouse);
            redirectAttributes.addFlashAttribute("successMessage", "Склад успешно добавлен!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении склада: " + e.getMessage());
        }
        return "redirect:/admin/addWarehouse"; // Перенаправляем на ту же страницу
    }

    @GetMapping("/editWarehouse/{id}")
    public String editWarehouseForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Warehouse> warehouseOpt = warehouseRepository.findById(id);
        if (warehouseOpt.isPresent()) {
            model.addAttribute("warehouse", warehouseOpt.get());
            model.addAttribute("pageTitle", "Редактирование склада");
            return "admin/editWarehouseForm"; // Возвращаем страницу с формой редактирования склада
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Склад не найден.");
            return "redirect:/admin/editWarehouse";
        }
    }
    @GetMapping("/editWarehouse")
    public String editWarehouse(Model model) {
        model.addAttribute("warehouses", warehouseRepository.findAll());
        return "admin/editWarehouse";
    }
    @PostMapping("/editWarehouse/{id}")
    @Transactional
    public String editWarehouseSubmit(@PathVariable("id") Integer id, @ModelAttribute Warehouse updatedWarehouse, RedirectAttributes redirectAttributes) {
        Optional<Warehouse> warehouseOpt = warehouseRepository.findById(id);
        if (warehouseOpt.isPresent()) {
            Warehouse existingWarehouse = warehouseOpt.get();
            existingWarehouse.setWarehouseName(updatedWarehouse.getWarehouseName());
            existingWarehouse.setLocation(updatedWarehouse.getLocation());
            existingWarehouse.setCapacity(updatedWarehouse.getCapacity());

            try {
                warehouseRepository.save(existingWarehouse);
                redirectAttributes.addFlashAttribute("successMessage", "Склад успешно обновлён!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении склада: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Склад не найден.");
        }
        return "redirect:/admin/editWarehouse"; // Перенаправляем на страницу редактирования
    }
    @GetMapping("/deleteWarehouse/{id}")
    public String deleteWarehouse(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            warehouseRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Категория успешно удалена!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при удалении категории: " + e.getMessage());
        }
        return "redirect:/admin/editCategory";
    }
    @GetMapping("/generateStockReport")
    @ResponseBody
    public ResponseEntity<byte[]> generateStockReport() throws UnsupportedEncodingException {
        List<Product> products = productRepository.findAll(); // Получаем все товары из базы данных

        StringBuilder csvBuilder = new StringBuilder();
        // Заголовки колонок на русском языке
        csvBuilder.append("ID,Название Товара,Описание,Категория,Количество,Цена за единицу,Склад\n");

        for (Product product : products) {
            csvBuilder.append(product.getId())
                    .append(",") // Разделитель между колонками
                    .append("\"").append(product.getProductName()).append("\"") // Обрамление значений в кавычки
                    .append(",")
                    .append("\"").append(product.getDescription()).append("\"")
                    .append(",")
                    .append("\"").append(product.getCategory().getCategoryName()).append("\"")
                    .append(",")
                    .append(product.getQuantityInStock())
                    .append(",")
                    .append(product.getUnitPrice())
                    .append(",")
                    .append("\"").append(product.getWarehouse().getWarehouseName()).append("\"")
                    .append("\n"); // Конец строки
        }

        // Кодировка Windows-1251
        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8); // Кодируем в UTF-8, а потом преобразуем в Windows-1251
        String encodedString = new String(csvBytes, StandardCharsets.UTF_8); // Получаем строку в UTF-8

        // Преобразуем строку в Windows-1251
        byte[] windows1251Bytes = encodedString.getBytes("Windows-1251");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=stock_report.csv");
        headers.add("Content-Type", "text/csv; charset=windows-1251"); // Указываем кодировку

        return new ResponseEntity<>(windows1251Bytes, headers, HttpStatus.OK);
    }

    @GetMapping("/generateCategoryReport")
    @ResponseBody
    public ResponseEntity<byte[]> generateCategoryReport() throws UnsupportedEncodingException {
        List<Category> categories = categoryRepository.findAll(); // Получаем все категории из базы данных

        StringBuilder csvBuilder = new StringBuilder();
        // Заголовки колонок
        csvBuilder.append("ID,Название Категории,Описание,Количество Товаров\n");

        for (Category category : categories) {
            long productCount = productRepository.countByCategoryId(category.getId()); // Получаем количество товаров в категории
            csvBuilder.append(category.getId())
                    .append(",")
                    .append("\"").append(category.getCategoryName()).append("\"")
                    .append(",")
                    .append("\"").append(category.getDescription()).append("\"")
                    .append(",")
                    .append(productCount)
                    .append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        String encodedString = new String(csvBytes, StandardCharsets.UTF_8);
        byte[] windows1251Bytes = encodedString.getBytes("Windows-1251");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=category_report.csv");
        headers.add("Content-Type", "text/csv; charset=windows-1251");

        return new ResponseEntity<>(windows1251Bytes, headers, HttpStatus.OK);
    }
    @GetMapping("/generateWarehouseReport")
    @ResponseBody
    public ResponseEntity<byte[]> generateWarehouseReport() throws UnsupportedEncodingException {
        List<Warehouse> warehouses = warehouseRepository.findAll(); // Получаем все склады из базы данных

        StringBuilder csvBuilder = new StringBuilder();
        // Заголовки колонок
        csvBuilder.append("ID,Название Склада,Расположение,Вместимость,Количество Товаров\n");

        for (Warehouse warehouse : warehouses) {
            long productCount = warehouseRepository.countById(warehouse.getId()); // Получаем количество товаров на складе
            csvBuilder.append(warehouse.getId())
                    .append(",")
                    .append("\"").append(warehouse.getWarehouseName()).append("\"")
                    .append(",")
                    .append("\"").append(warehouse.getLocation()).append("\"")
                    .append(",")
                    .append(warehouse.getCapacity())
                    .append(",")
                    .append(productCount)
                    .append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        String encodedString = new String(csvBytes, StandardCharsets.UTF_8);
        byte[] windows1251Bytes = encodedString.getBytes("Windows-1251");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=warehouse_report.csv");
        headers.add("Content-Type", "text/csv; charset=windows-1251");

        return new ResponseEntity<>(windows1251Bytes, headers, HttpStatus.OK);
    }

}
