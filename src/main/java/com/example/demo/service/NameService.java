package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NameService {

    private final UserRepository userRepository;

    public void cleanShortNames() {
        List<User> allUsers = userRepository.findAll();
        int total = allUsers.size();
        int removed = 0;

        long skippedNull = allUsers.stream()
                .filter(user -> user.getName() == null)
                .peek(user -> log.warn("Пропущен пользователь с NULL именем (id={})", user.getId()))
                .count();

        var usersToDelete = allUsers.stream()
                .filter(user -> user.getName() != null && user.getName().length() < 3)
                .toList();
        usersToDelete.forEach(u -> {
            userRepository.delete(u);
            log.info("Удален пользователь: id={}, name={}", u.getId(), u.getName());
        });
        removed = usersToDelete.size();

        /*for (User user : allUsers) {
            String name = user.getName();
            if (name == null) {
                log.warn("Пропущен пользователь с NULL именем (id={})", user.getId());
                continue;
            }

            if (name.length() < 3) {
                userRepository.delete(user);
                removed++;
                log.info("Удалён пользователь: id={},name={}", user.getId(), name);
            }
        }*/

        log.info("Проверка завершена: всего=" + total + ", удалено=" + removed + ", осталось=" + (total - removed));
    }
}
