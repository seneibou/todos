package sn.ept.git.seminaire.cicd.services.impl;

import sn.ept.git.seminaire.cicd.dto.TagDTO;
import sn.ept.git.seminaire.cicd.dto.vm.TagVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemExistsException;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.TagMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.TagVMMapper;
import sn.ept.git.seminaire.cicd.models.Tag;
import sn.ept.git.seminaire.cicd.repositories.TagRepository;
import sn.ept.git.seminaire.cicd.services.ITagService;
import sn.ept.git.seminaire.cicd.utils.ExceptionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements ITagService {

    private final TagRepository repository;
    private final TagMapper mapper;
    private final TagVMMapper vmMapper;

    public TagServiceImpl(TagRepository repository, TagMapper mapper, TagVMMapper vmMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.vmMapper = vmMapper;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public TagDTO save(TagVM vm) {
        final Optional<Tag> optional = repository.findByName(vm.getName());
        ExceptionUtils.absentOrThrow(optional, ItemExistsException.NAME_EXISTS, vm.getName());
        return mapper.asDTO(repository.saveAndFlush(vmMapper.asEntity(vm)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(UUID uuid) {
        final Optional<Tag> optional = repository.findById(uuid);
        if(optional.isPresent()){
            final Tag site = optional.get();
            site.setDeleted(true);
            repository.saveAndFlush(site);
            return;
        }
        throw new ItemNotFoundException(
                ItemNotFoundException.format(ItemNotFoundException.TAG_BY_ID, uuid.toString())
        );
    }

    @Override
    public Optional<TagDTO> findById(UUID uuid) {
        return repository
                .findById(uuid)
                .map(mapper::asDTO);
    }

    @Override
    public List<TagDTO> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::asDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TagDTO> findAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::asDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public TagDTO update(UUID uuid, TagVM vm) {

        Optional<Tag>  optional = repository.findByNameWithIdNotEquals(vm.getName(),uuid);
        ExceptionUtils.absentOrThrow(optional, ItemExistsException.TITLE_EXISTS, vm.getName());
        optional = repository.findById(uuid);
        if(optional.isPresent()){
            final Tag item = optional.get();
            item.setName(vm.getName());
            item.setDescription(vm.getDescription());
            return mapper.asDTO(repository.saveAndFlush(item));
        }


        throw new ItemNotFoundException(
                ItemNotFoundException.format(ItemNotFoundException.TAG_BY_ID, uuid.toString())
        );
    }

    @Transactional
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
