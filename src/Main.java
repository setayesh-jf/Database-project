

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.service.StepService;
import todo.service.TaskService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Welcome to the Task Management System!");
        System.out.println("Type 'exit' to quit the program.");
        System.out.println("Available commands:");
        System.out.println("- add task");
        System.out.println("- add step");
        System.out.println("- delete");
        System.out.println("- update task");
        System.out.println("- update step");
        System.out.println("- get task-by-id");
        System.out.println("- get all-tasks");
        System.out.println("- get incomplete-tasks");

        while (true) {
            try {
                System.out.print("\nEnter a command: ");
                String command = scanner.nextLine().trim().toLowerCase();

                switch (command) {
                    case "exit":
                        System.out.println("Exiting the program...");
                        scanner.close();
                        return;

                    case "add task":
                        handleAddTask(scanner, dateFormat);
                        break;

                    case "add step":
                        handleAddStep(scanner);
                        break;

                    case "delete":
                        handleDeleteEntity(scanner);
                        break;

                    case "update task":
                        handleUpdateTask(scanner);
                        break;

                    case "update step":
                        handleUpdateStep(scanner);
                        break;

                    case "get task-by-id":
                        handleGetTaskById(scanner);
                        break;

                    case "get all-tasks":
                        handleGetAllTasks();
                        break;

                    case "get incomplete-tasks":
                        handleGetIncompleteTasks();
                        break;

                    default:
                        System.out.println("Unknown command. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private static void handleAddTask(Scanner scanner, SimpleDateFormat dateFormat) {
        try {
            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Due date (yyyy-MM-dd): ");
            String dueDateInput = scanner.nextLine();

            if (!dueDateInput.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            Date dueDate = dateFormat.parse(dueDateInput);
            Task task = TaskService.addTask(title, description, dueDate);

            System.out.println("Task saved successfully.");
            System.out.println("ID: " + task.getId());
        } catch (InvalidEntityException e) {
            System.out.println("Cannot save task. Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid date format or input. Please use yyyy-MM-dd.");
        }
    }

    private static void handleAddStep(Scanner scanner) {
        try {
            System.out.print("Task ID: ");
            String taskIdInput = scanner.nextLine();

            if (!taskIdInput.matches("\\d+")) {
                System.out.println("Invalid Task ID. Please provide a numeric value.");
                return;
            }

            int taskRef = Integer.parseInt(taskIdInput);

            System.out.print("Step Title: ");
            String stepTitle = scanner.nextLine();

            Step step = StepService.saveStep(taskRef, stepTitle);

            System.out.println("Step saved successfully.");
            System.out.println("ID: " + step.getId());
        } catch (InvalidEntityException | EntityNotFoundException e) {
            System.out.println("Cannot save step. Error: " + e.getMessage());
        }
    }

    private static void handleDeleteEntity(Scanner scanner) {
        try {
            System.out.print("Entity ID: ");
            String entityIdInput = scanner.nextLine();

            if (!entityIdInput.matches("\\d+")) {
                System.out.println("Invalid Entity ID. Please provide a numeric value.");
                return;
            }

            int entityId = Integer.parseInt(entityIdInput);
            TaskService.deleteTask(entityId);

            System.out.println("Entity with ID=" + entityId + " successfully deleted.");
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot delete entity. Error: " + e.getMessage());
        }
    }

    private static void handleUpdateTask(Scanner scanner) {
        try {
            System.out.print("Task ID: ");
            String taskIdInput = scanner.nextLine();

            if (!taskIdInput.matches("\\d+")) {
                System.out.println("Invalid Task ID. Please provide a numeric value.");
                return;
            }

            int taskId = Integer.parseInt(taskIdInput);

            System.out.print("Field to update (title/status): ");
            String field = scanner.nextLine().trim().toLowerCase();

            if (field.equals("title")) {
                System.out.print("New Title: ");
                String newTitle = scanner.nextLine();

                TaskService.updateTaskTitle(taskId, newTitle);
                System.out.println("Task updated successfully.");
            } else if (field.equals("status")) {
                System.out.print("New Status (NotStarted/InProgress/Completed): ");
                String newStatusInput = scanner.nextLine();

                Task.Status newStatus = Task.Status.valueOf(newStatusInput);
                TaskService.updateTaskStatus(taskId, newStatus);

                System.out.println("Task status updated successfully.");
            } else {
                System.out.println("Invalid field. Please enter 'title' or 'status'.");
            }
        } catch (InvalidEntityException | EntityNotFoundException e) {
            System.out.println("Cannot update task. Error: " + e.getMessage());
        }
    }

    private static void handleUpdateStep(Scanner scanner) {
        try {
            System.out.print("Step ID: ");
            String stepIdInput = scanner.nextLine();

            if (!stepIdInput.matches("\\d+")) {
                System.out.println("Invalid Step ID. Please provide a numeric value.");
                return;
            }

            int stepId = Integer.parseInt(stepIdInput);

            System.out.print("Field to update (title/status): ");
            String field = scanner.nextLine().trim().toLowerCase();

            if (field.equals("title")) {
                System.out.print("New Title: ");
                String newTitle = scanner.nextLine();

                StepService.updateStepTitle(stepId, newTitle);
                System.out.println("Step updated successfully.");
            } else if (field.equals("status")) {
                System.out.print("New Status (NotStarted/Completed): ");
                String newStatusInput = scanner.nextLine();

                Step.Status newStatus = Step.Status.valueOf(newStatusInput);
                StepService.setStepStatus(stepId, newStatus);

                System.out.println("Step status updated successfully.");
            } else {
                System.out.println("Invalid field. Please enter 'title' or 'status'.");
            }
        } catch (InvalidEntityException | EntityNotFoundException e) {
            System.out.println("Cannot update step. Error: " + e.getMessage());
        }
    }

    private static void handleGetTaskById(Scanner scanner) {
        try {
            System.out.print("Task ID: ");
            String taskIdInput = scanner.nextLine();

            if (!taskIdInput.matches("\\d+")) {
                System.out.println("Invalid Task ID. Please provide a numeric value.");
                return;
            }

            int taskId = Integer.parseInt(taskIdInput);
            Task task = TaskService.getTask(taskId);

            System.out.println("ID: " + task.getId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Due Date: " + task.getDueDate());
            System.out.println("Status: " + task.getStatus());
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot find task with ID. Error: " + e.getMessage());
        }
    }

    private static void handleGetAllTasks() {
        System.out.println("Fetching all tasks...");
    }

    private static void handleGetIncompleteTasks() {
        System.out.println("Fetching incomplete tasks...");
    }
}
