package sn.ept.git.seminaire.cicd.resources;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import sn.ept.git.seminaire.cicd.data.TodoVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.TodoDTO;
import sn.ept.git.seminaire.cicd.dto.vm.TodoVM;
import sn.ept.git.seminaire.cicd.services.ITodoService;
import sn.ept.git.seminaire.cicd.utils.SizeMapping;
import sn.ept.git.seminaire.cicd.utils.TestUtil;
import sn.ept.git.seminaire.cicd.utils.UrlMapping;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class TodoResourceTest extends BasicResourceTest {

        @Autowired
        private ITodoService service;
        private TodoDTO dto;
        private TodoVM vm;

        // should consider tags in add and update methods

        @BeforeAll
        static void beforeAll() {
                log.info(" before all ");
        }

        @BeforeEach
        void beforeEach() {
                log.info(" before each ");
                service.deleteAll();
                vm = TodoVMTestData.defaultVM();
        }

        @Test
        void findAll_shouldReturnTodos() throws Exception {
                dto = service.save(vm);
                mockMvc.perform(get(UrlMapping.Todo.ALL)
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content", hasSize(1)))
                                .andExpect(jsonPath("$.content.[0].id").exists())
                                .andExpect(jsonPath("$.content.[0].version").exists())
                                .andExpect(jsonPath("$.content.[0].enabled").exists())
                                .andExpect(jsonPath("$.content.[0].deleted").exists())
                                .andExpect(jsonPath("$.content.[0].enabled", is(true)))
                                .andExpect(jsonPath("$.content.[0].deleted").value(false))
                                .andExpect(jsonPath("$.content.[0].title", is(dto.getTitle())))
                                .andExpect(jsonPath("$.content.[0].description").value(dto.getDescription()));

        }

        @Test
        void findById_shouldReturnTodo() throws Exception {
                dto = service.save(vm);
                mockMvc.perform(get(UrlMapping.Todo.FIND_BY_ID, dto.getId())
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").exists())
                                .andExpect(jsonPath("$.version").exists())
                                .andExpect(jsonPath("$.enabled").exists())
                                .andExpect(jsonPath("$.deleted").exists())
                                .andExpect(jsonPath("$.title", is(dto.getTitle())))
                                .andExpect(jsonPath("$.description").value(dto.getDescription()));
        }

        @Test
        void findById_withBadId_shouldReturnNotFound() throws Exception {
                mockMvc.perform(get(UrlMapping.Todo.FIND_BY_ID, UUID.randomUUID().toString())
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

        @Test
        void add_shouldCreateTodo() throws Exception {
                mockMvc.perform(
                                post(UrlMapping.Todo.ADD)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(TestUtil.convertObjectToJsonBytes(vm)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").exists())
                                .andExpect(jsonPath("$.version").exists())
                                .andExpect(jsonPath("$.enabled").exists())
                                .andExpect(jsonPath("$.deleted").exists())
                                .andExpect(jsonPath("$.title").value(vm.getTitle()))
                                .andExpect(jsonPath("$.description").value(vm.getDescription()));
        }

        @Test
        void add_withTitleMinLengthExceeded_shouldReturnBadRequest() throws Exception {
                vm.setTitle(RandomStringUtils.random(SizeMapping.Title.MIN - 1));
                mockMvc.perform(post(UrlMapping.Todo.ADD)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(vm)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void add_withTitleMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
                vm.setTitle(RandomStringUtils.random(SizeMapping.Title.MAX + 1));
                mockMvc.perform(post(UrlMapping.Todo.ADD)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(vm)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void update_shouldUpdateTodo() throws Exception {
                dto = service.save(vm);
                vm.setTitle(TestData.Update.title);
                vm.setDescription(TestData.Update.description);
                mockMvc.perform(
                                put(UrlMapping.Todo.UPDATE, dto.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(TestUtil.convertObjectToJsonBytes(vm)))
                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$.id").exists())
                                .andExpect(jsonPath("$.version").exists())
                                .andExpect(jsonPath("$.enabled").exists())
                                .andExpect(jsonPath("$.deleted").exists())
                                .andExpect(jsonPath("$.title").value(vm.getTitle()))
                                .andExpect(jsonPath("$.description").value(vm.getDescription()));
        }

        @Test
        void update_withTitleMinLengthExceeded_shouldReturnBadRequest() throws Exception {
                dto = service.save(vm);
                vm.setTitle(RandomStringUtils.random(SizeMapping.Title.MIN - 1));
                mockMvc.perform(put(UrlMapping.Todo.UPDATE, dto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(vm)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void update_withTitleMaxLengthExceeded_shouldReturnBadRequest() throws Exception {
                dto = service.save(vm);
                vm.setTitle(RandomStringUtils.random(SizeMapping.Title.MAX + 1));
                mockMvc.perform(put(UrlMapping.Todo.UPDATE, dto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(vm)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void delete_shouldDeleteTodo() throws Exception {
                dto = service.save(vm);
                mockMvc.perform(
                                delete(UrlMapping.Todo.DELETE, dto.getId())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent());
        }

        @Test
        void delete_withBadId_shouldReturnNotFound() throws Exception {
                dto = service.save(vm);
                mockMvc.perform(
                                delete(UrlMapping.Todo.DELETE, UUID.randomUUID().toString())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }

        // java 8 requis,

        // vos tests ici
        @Test
        void complete_shouldCompleteTodo() throws Exception {
                dto = service.save(vm);
                mockMvc.perform(
                                delete(UrlMapping.Todo.COMPLETE, dto.getId())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(TestUtil.convertObjectToJsonBytes(vm)))
                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$.id").exists())
                                .andExpect(jsonPath("$.version").exists())
                                .andExpect(jsonPath("$.enabled").exists())
                                .andExpect(jsonPath("$.deleted").exists())
                                .andExpect(jsonPath("$.title").value(vm.getTitle()))
                                .andExpect(jsonPath("$.description").value(vm.getDescription()))
                                .andExpect(jsonPath("$.completed").value(true))
                                .andExpect(jsonPath("$.size()").value(1));
        }

        @Test
        void complete_withBadId_shouldThrowException() throws Exception {
                dto = service.save(vm);
                mockMvc.perform(
                                delete(UrlMapping.Todo.COMPLETE, UUID.randomUUID().toString())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound());
        }
}