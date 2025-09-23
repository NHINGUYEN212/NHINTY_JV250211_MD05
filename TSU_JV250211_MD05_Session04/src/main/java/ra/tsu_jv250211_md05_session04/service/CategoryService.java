package ra.tsu_jv250211_md05_session04.service;

import ra.tsu_jv250211_md05_session04.model.dto.CategoryDTO;
import ra.tsu_jv250211_md05_session04.model.entity.Category;
import ra.tsu_jv250211_md05_session04.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(() ->  new NoSuchElementException("Category not found"));
    }

    public String saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.save(Category
                .builder()
                .categoryName(categoryDTO.getCategoryName())
                .description(categoryDTO.getDescription())
                .build());
        if (category != null) {
            return "Thêm mới danh mục thành công !" ;
        }else {
            return "Có lỗi , thêm mới danh mục thất bại !" ;
        }
    }


    public String updateCategory(CategoryDTO categoryDTO , long id) {
        Category category = getCategoryById(id);
        if(category != null) {
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setDescription(categoryDTO.getDescription());
            Category newCategory =  categoryRepository.save(category);
            if(newCategory != null) {
                return "Cập nhật danh mục thành công !";
            }else {
                return "Cập nhật danh mục thất bại !" ;
            }
        }else {
            return  "Category id not found";
        }

    }

    public String deleteCategoryById(long id) {
        Category category = getCategoryById(id);
        if(category != null) {
            try {
                categoryRepository.delete(category);
                return "Xóa danh mục thành công !";
            } catch (Exception e) {
                return "Xóa danh mục thất bại !" ;
            }
        }else {
            return  "Category id not found";
        }
    }
}
