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

        for (User user : allUsers) {
            String name = user.getName();
            if (name == null) {
                log.warn("⚠ Пропущен пользователь с NULL именем (id=" + user.getId() + ")");
                continue;
            }

            if (name.length() < 3) {
                userRepository.delete(user);
                removed++;
                log.info("🗑 Удалён пользователь: id=" + user.getId() + ", name=" + name);
            }
        }

        log.info("✅ Проверка завершена: всего=" + total + ", удалено=" + removed + ", осталось=" + (total - removed));
    }
}
