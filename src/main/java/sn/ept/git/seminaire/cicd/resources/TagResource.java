package sn.ept.git.seminaire.cicd.resources;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import sn.ept.git.seminaire.cicd.dto.TagDTO;
import sn.ept.git.seminaire.cicd.dto.base.BaseDTO;
import sn.ept.git.seminaire.cicd.dto.vm.TagVM;
import sn.ept.git.seminaire.cicd.services.ITagService;
import sn.ept.git.seminaire.cicd.utils.ResponseUtil;
import sn.ept.git.seminaire.cicd.utils.UrlMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Validated
@RestController
public class TagResource {

    private final ITagService service;

    public TagResource(ITagService service) {
        this.service = service;
    }

    @GetMapping(UrlMapping.Tag.ALL)
    public ResponseEntity<Page<TagDTO>> findAll(
            @PageableDefault Pageable page
    ) {
        Page<TagDTO> result = service.findAll(page);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(UrlMapping.Tag.FIND_BY_ID)
    public ResponseEntity<TagDTO> findById(@PathVariable ("id") UUID id) {
        return ResponseUtil.wrapOrNotFound(service.findById(id),HttpStatus.OK);
    }

    @PostMapping(UrlMapping.Tag.ADD)
    public ResponseEntity<TagDTO> create(@RequestBody @Valid TagVM vm) {

        TagDTO created = service.save(vm);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(UrlMapping.Tag.DELETE)
    public ResponseEntity<TagDTO> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(UrlMapping.Tag.UPDATE)
    public ResponseEntity<TagDTO> update(
            @PathVariable("id") UUID id,
            @RequestBody @Valid TagVM vm) {
        final TagDTO dto = service.update(id, vm);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dto),HttpStatus.ACCEPTED);
    }


    @PostMapping(UrlMapping.Tag.ADD_ALL)
    public ResponseEntity<List<TagDTO>> addALL(@RequestBody List<  @Valid TagVM> vms) {
        List<TagDTO >  created =  service.addALL(vms);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{ids}")
                .buildAndExpand(created.stream().map(BaseDTO::getId).collect(Collectors.toList()))
                .toUri();
        return ResponseEntity.created(location).body(created);
    }




}
