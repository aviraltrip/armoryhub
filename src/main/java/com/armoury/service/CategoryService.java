package com.armoury.service;

import com.armoury.model.WeaponCategory;
import com.armoury.repository.WeaponCategoryRepository;
import com.armoury.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final WeaponCategoryRepository categoryRepository;
    private final AuditLogService auditLogService;

    @Autowired
    public CategoryService(WeaponCategoryRepository categoryRepository, AuditLogService auditLogService) {
        this.categoryRepository = categoryRepository;
        this.auditLogService = auditLogService;
    }

    public WeaponCategory createCategory(WeaponCategory category) {
        java.util.Optional<WeaponCategory> existing = categoryRepository.findByName(category.getName());
        if (existing.isPresent()) {
            return existing.get();
        }
        WeaponCategory saved = categoryRepository.save(category);
        auditLogService.logAction("CREATE_CATEGORY", "Created weapon category: " + saved.getName(), "ADMIN");
        return saved;
    }

    public List<WeaponCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public WeaponCategory getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weapon category not found with ID: " + id));
    }
}
