package by.davlar.service;

import by.davlar.dto.RoleDto;
import by.davlar.hibernate.dao.RoleDao;
import by.davlar.mapper.RoleToDtoMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleService {
    private static final RoleService INSTANCE = new RoleService();
    private final RoleDao roleDao = RoleDao.getInstance();
    private final RoleToDtoMapper roleToDtoMapper = RoleToDtoMapper.getInstance();

    public static RoleService getInstance() {
        return INSTANCE;
    }

    public List<RoleDto> findAll() {
        return roleDao.findAll().stream()
                .map(roleToDtoMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public Optional<RoleDto> findByName(String name) {
        return roleDao.findByName(name)
                .map(roleToDtoMapper::mapFrom);
    }

    public Integer getDefaultId() {
        return roleDao.getDefaultId();
    }
}
