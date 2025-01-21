package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Notification;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import com.example.demo.entity.TaskStatus;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.TaskRepository;

@Service
public class TaskNotificationService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;  // Optional for persistence
	
	//Check for notifications
	@Scheduled(cron = "0 1/1 * * * *")
	public void checkTasksForNotification() {
		LocalDate today = LocalDate.now();
		
		// Notify for high-priority tasks
		List<Task> highPriorityTasks = taskRepository.findByPriority(TaskPriority.HIGH);
		highPriorityTasks.forEach(task -> sendNotification(task, "High-priority task created: " + task.getTitle()));
		
		// Notify for overdue pending tasks
		List<Task> pendingTasks = taskRepository.findByStatus(TaskStatus.PENDING);
		pendingTasks.forEach(task -> {
			if(task.getDueDate().isBefore(today.minusDays(3))) {
				sendNotification(task, "Task is pending for too long: " + task.getTitle());
			}
		});
		
		// Notify for in-progress tasks near deadline
        List<Task> inProgressTasks = taskRepository.findByStatus(TaskStatus.IN_PROGRESS);
        inProgressTasks.forEach(task -> {
            if (task.getDueDate().isBefore(today.plusDays(2))) {
                sendNotification(task, "In-progress task is nearing its deadline: " + task.getTitle());
            }
        });
	}
	
	//send notification
	private void sendNotification(Task task, String message) {
		System.out.println("Sending notification for task " + task.getId() + ": " + message);
		
		// Save notification to DB (Optional)
		Notification notification = new Notification();
		notification.setTaskId(task.getId());
		notification.setMessage(message);
		notification.setCreatedAt(LocalDate.now());
		notificationRepository.save(notification);
	}
}
