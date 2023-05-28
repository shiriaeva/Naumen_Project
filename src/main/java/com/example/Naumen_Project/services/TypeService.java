package com.example.Naumen_Project.services;

import com.example.Naumen_Project.dto.TypeCommon;
import com.example.Naumen_Project.models.Type;
import com.example.Naumen_Project.repositories.TypeRepository;
import org.springframework.stereotype.Service;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public void saveType(TypeCommon typeDTO) {
        if (!typeRepository.existsByName(typeDTO.getName())) {
            var result = new Type();
            result.setName(typeDTO.getName());
            typeRepository.save(result);
        }
    }

    public Type getTypeByName(String type) {
        return typeRepository.findByName(type).orElseThrow(()->new IllegalArgumentException("Типа "+type+" не существует"));
    }
}
