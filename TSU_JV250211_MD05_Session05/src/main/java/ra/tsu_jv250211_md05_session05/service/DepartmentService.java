package ra.tsu_jv250211_md05_session05.service;

import ra.tsu_jv250211_md05_session05.model.dto.DepartmentDTO;
import ra.tsu_jv250211_md05_session05.model.entity.Department;
import ra.tsu_jv250211_md05_session05.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department addDepartment(DepartmentDTO departmentDTO) {
        return departmentRepository.save(convertDepartmentDTOToDepartment(departmentDTO));
    }

    public Department updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = findById(id);
        if (department != null) {
            Department newDepartment = convertDepartmentDTOToDepartment(departmentDTO);
            department.setId(id);
            return departmentRepository.save(newDepartment);
        }else {
            return null;
        }

    }

    public String deleteDepartment(Long id) {
        Department department = findById(id);
        if (department != null) {
            try {
                departmentRepository.delete(department);
                return "Department deleted successfully";
            }catch (Exception e) {
                return "Department deletion failed";
            }
        }else {
            return "Department not found";
        }
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department convertDepartmentDTOToDepartment(DepartmentDTO departmentDTO) {
        return Department
                .builder()
                .departmentName(departmentDTO.getDepartmentName())
                .description(departmentDTO.getDescription())
                .build();
    }
}
