package by.davlar.spring.service;

import by.davlar.spring.database.repository.RoleRepository;
import by.davlar.spring.dto.RoleDto;
import by.davlar.spring.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleDao;
    private final RoleMapper roleMapper = RoleMapper.INSTANCE;

    public List<RoleDto> findAll() {
        return roleDao.findAll().stream()
                .map(roleMapper::RoleToDto)
                .collect(Collectors.toList());
    }

    public Optional<RoleDto> findByName(String name) {
        return roleDao.findByName(name)
                .map(roleMapper::RoleToDto);
    }

    public Optional<RoleDto> getDefault() {
        return roleDao.findById(roleDao.getDefaultId())
                .map(roleMapper::RoleToDto);
    }
}
