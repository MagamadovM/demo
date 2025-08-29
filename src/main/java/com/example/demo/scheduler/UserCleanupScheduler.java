package com.example.demo.scheduler;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCleanupScheduler {

    private final UserRepository userRepository;

    public UserCleanupScheduler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // каждые 10 секунд
    @Scheduled(cron = "*/10 * * * * *")
    public void cleanShortNames() {
        List<User> allUsers = userRepository.findAll();
        int total = allUsers.size();
        int removed = 0;

        for (User user : allUsers) {
            String name = user.getName();
            if (name == null) {
                System.out.println("⚠ Пропущен пользователь с NULL именем (id=" + user.getId() + ")");
                continue;
            }

            if (name.length() < 3) {
                userRepository.delete(user);
                removed++;
                System.out.println("🗑 Удалён пользователь: id=" + user.getId() + ", name=" + name);
            }
        }

        System.out.println("✅ Проверка завершена: всего=" + total + ", удалено=" + removed + ", осталось=" + (total - removed));
    }
}