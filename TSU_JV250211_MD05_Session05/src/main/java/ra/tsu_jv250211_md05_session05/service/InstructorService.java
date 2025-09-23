package ra.tsu_jv250211_md05_session05.service;

import ra.tsu_jv250211_md05_session05.model.dto.InstructorDTO;
import ra.tsu_jv250211_md05_session05.model.entity.Instructor;
import ra.tsu_jv250211_md05_session05.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private DepartmentService departmentService;

    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }

    public Instructor findById(Long id) {
        return instructorRepository.findById(id).orElse(null);
    }

    public Instructor save(InstructorDTO instructorDTO) {
        return instructorRepository.save(convertInstructorDTOToInstructor(instructorDTO));
    }

    public Instructor update(Long id, InstructorDTO instructorDTO) {
        Instructor instructor = findById(id);
        if (instructor != null) {
            Instructor updatedInstructor = convertInstructorDTOToInstructor(instructorDTO);
            updatedInstructor.setId(id);
            return instructorRepository.save(updatedInstructor);
        }else {
            return null;
        }

    }

    public String delete(Long id) {
        Instructor instructor = findById(id);
        if (instructor != null) {
            try {
                instructorRepository.delete(instructor);
                return "Instructor deleted";
            }catch (Exception e) {
                return "Instructor delete failed";
            }
        }else {
            return "Instructor not found";
        }

    }

    public Instructor convertInstructorDTOToInstructor(InstructorDTO instructorDTO) {
        return Instructor
                .builder()
                .name(instructorDTO.getName())
                .email(instructorDTO.getEmail())
                .department(departmentService.findById(instructorDTO.getDepartmentId()))
                .build();
    }
}
