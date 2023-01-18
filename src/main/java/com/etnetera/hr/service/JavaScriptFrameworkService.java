package com.etnetera.hr.service;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JavaScriptFrameworkRequestDTO;
import com.etnetera.hr.exception.JavaScriptFrameworkException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JavaScriptFrameworkService {

    private final JavaScriptFrameworkRepository repository;

    public Iterable<JavaScriptFramework> getAllFrameworks() {
        return repository.findAll();
    }

    public JavaScriptFramework addNewFramework(JavaScriptFrameworkRequestDTO dto) {
        var newName = StringUtils.trim(dto.getName());
        var newVersion = StringUtils.trim(dto.getVersion());

        if (repository.existsByNameIgnoreCaseAndVersionIgnoreCase(newName, newVersion)) {
            throw new JavaScriptFrameworkException(HttpStatus.CONFLICT, "Framework " + newName + " with version " + newVersion + " already exists.");
        }

        var newFramework = JavaScriptFramework.builder()
                .name(newName)
                .version(newVersion)
                .depricationDate(dto.getDepricationDate())
                .hypeLevel(dto.getHypeLevel())
                .build();

        return repository.save(newFramework);
    }

    public JavaScriptFramework updateFramework(long id, JavaScriptFrameworkRequestDTO dto) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new JavaScriptFrameworkException(HttpStatus.NOT_FOUND, "Framework with id " + id + " does not exist."));

        existing.setName(dto.getName());
        existing.setVersion(dto.getVersion());
        existing.setDepricationDate(dto.getDepricationDate());
        existing.setHypeLevel(dto.getHypeLevel());

        return repository.save(existing);
    }

    public void deleteFramework(long id) throws JavaScriptFrameworkException {
        if (!repository.existsById(id))
            throw new JavaScriptFrameworkException(HttpStatus.NOT_FOUND, "Framework with id " + id + " does not exist.");

        repository.deleteById(id);
    }
}
