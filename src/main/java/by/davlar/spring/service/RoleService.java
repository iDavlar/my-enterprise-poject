package by.davlar.spring.service;

import by.davlar.spring.database.repository.RoleRepository;
import by.davlar.spring.dto.RoleDto;
import by.davlar.spring.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleDao;
    private final RoleMapper roleMapper = RoleMapper.INSTANCE;

    public List<RoleDto> findAll() {
        log.info("findAll()");
        return roleDao.findAll().stream()
                .map(roleMapper::RoleToDto)
                .collect(Collectors.toList());
    }

    public Optional<RoleDto> findByName(String name) {
        log.info("findByName(name = {})", name);
        return roleDao.findByName(name)
                .map(roleMapper::RoleToDto);
    }

    public Optional<RoleDto> getDefault() {
        log.info("getDefault()");
        return roleDao.findById(roleDao.getDefaultId())
                .map(roleMapper::RoleToDto);
    }
}
