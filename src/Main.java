

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.service.StepService;
import todo.service.TaskService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.List;

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
                        handleGetIncompleteTasks(scanner);
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
            System.out.println("ID: " + task.id);
        } catch (InvalidEntityException e) {
            System.out.println("Cannot save task.");
            System.out.println("Error: " + e.getMessage());
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

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            String creationDateFormatted = dateFormat.format(step.getCreationDate());

            System.out.println("Step saved successfully.");
            System.out.println("ID: " + step.id);
            System.out.println("Creation Date: " + creationDateFormatted);
        } catch (InvalidEntityException | EntityNotFoundException e) {
            System.out.println("Cannot save step.");
            System.out.println("Error: " + e.getMessage());
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

            Task task = TaskService.getTask(taskId);

            switch (field) {
                case "title":
                    System.out.print("New Title: ");
                    String newTitle = scanner.nextLine();

                    if (newTitle == null || newTitle.trim().isEmpty()) {
                        System.out.println("New title cannot be empty.");
                        return;
                    }


                    String oldTitle = task.getTitle();
                    TaskService.updateTaskTitle(taskId, newTitle);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                    String modificationDate = dateFormat.format(new Date());


                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: title");
                    System.out.println("Old Value: " + oldTitle);
                    System.out.println("New Value: " + newTitle);
                    System.out.println("Modification Date: " + modificationDate);
                    break;

                case "status":
                    System.out.print("New Status (NotStarted/InProgress/Completed): ");
                    String newStatusInput = scanner.nextLine();

                    try {
                        Task.Status newStatus = Task.Status.valueOf(newStatusInput);
                        Task.Status oldStatus = task.getStatus();

                        TaskService.updateTaskStatus(taskId, newStatus);
                        String modificationDateForStatus = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").format(new Date());


                        System.out.println("Successfully updated the task.");
                        System.out.println("Field: status");
                        System.out.println("Old Value: " + oldStatus);
                        System.out.println("New Value: " + newStatus);
                        System.out.println("Modification Date: " + modificationDateForStatus);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status value. Please use NotStarted, InProgress, or Completed.");
                    }
                    break;

                default:
                    System.out.println("Invalid field. Please enter 'title' or 'status'.");
            }
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot find task with ID=" + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }



    private static void handleUpdateStep(Scanner scanner) {
        String stepIdInput = null;

        try {
            System.out.print("Step ID: ");
            stepIdInput = scanner.nextLine();

            if (!stepIdInput.matches("\\d+")) {
                System.out.println("Invalid Step ID. Please provide a numeric value.");
                return;
            }

            int stepId = Integer.parseInt(stepIdInput);

            System.out.print("Field to update (title/status): ");
            String field = scanner.nextLine().trim().toLowerCase();

            Step step = StepService.getStepById(stepId);

            switch (field) {
                case "title":
                    System.out.print("New Title: ");
                    String newTitle = scanner.nextLine();

                    if (newTitle == null || newTitle.trim().isEmpty()) {
                        System.out.println("New title cannot be empty.");
                        return;
                    }


                    String oldTitle = step.getTitle();


                    StepService.updateStepTitle(stepId, newTitle);


                    System.out.println("Successfully updated the step.");
                    System.out.println("Field: title");
                    System.out.println("Old Value: " + oldTitle);
                    System.out.println("New Value: " + newTitle);
                    System.out.println("Modification Date: " + new Date());
                    break;

                case "status":
                    System.out.print("New Status (NotStarted/Completed): ");
                    String newStatusInput = scanner.nextLine();

                    try {
                        Step.Status newStatus = Step.Status.valueOf(newStatusInput);
                        Step.Status oldStatus = step.getStatus();


                        StepService.setStepStatus(stepId, newStatus);


                        StepService.checkAndUpdateTaskStatus(step.getTaskRef());


                        System.out.println("Successfully updated the step.");
                        System.out.println("Field: status");
                        System.out.println("Old Value: " + oldStatus);
                        System.out.println("New Value: " + newStatus);
                        System.out.println("Modification Date: " + new Date());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status value. Please use NotStarted or Completed.");
                    }
                    break;

                default:
                    System.out.println("Invalid field. Please enter 'title' or 'status'.");
            }
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot update step with ID=" + stepIdInput);
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidEntityException e) {
            System.out.println("Cannot update step. Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    private static void handleGetTaskById(Scanner scanner) {
        String taskIdInput = null;

        try {
            System.out.print("Task ID: ");
            taskIdInput = scanner.nextLine();

            if (!taskIdInput.matches("\\d+")) {
                System.out.println("Invalid Task ID. Please provide a numeric value.");
                return;
            }

            int taskId = Integer.parseInt(taskIdInput);

            Task task = TaskService.getTask(taskId);

            System.out.println("ID: " + task.id);
            System.out.println("Title: " + task.getTitle());
            System.out.println("Due Date: " + new SimpleDateFormat("yyyy-MM-dd").format(task.getDueDate()));
            System.out.println("Status: " + task.getStatus());
            System.out.println("Steps:");

            List<Step> steps = StepService.getStepsForTask(taskId);

            for (Step step : steps) {
                System.out.println("    + " + step.getTitle() + ":");
                System.out.println("        ID: " + step.id);
                System.out.println("        Status: " + step.getStatus());
            }
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot find task with ID=" + taskIdInput);
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    private static void handleGetAllTasks() {
        try {
            List<Task> tasks = TaskService.getAllTasks();

            if (tasks.isEmpty()) {
                System.out.println("No tasks available.");
                return;
            }


            for (Task task : tasks) {
                System.out.println("ID: " + task.id);
                System.out.println("Title: " + task.getTitle());
                System.out.println("Due Date: " + new SimpleDateFormat("yyyy-MM-dd").format(task.getDueDate()));
                System.out.println("Status: " + task.getStatus());
                System.out.println("Steps:");

                List<Step> steps = StepService.getStepsForTask(task.id);

                if (steps.isEmpty()) {
                    System.out.println("    No steps available for this task.");
                } else {
                    for (Step step : steps) {
                        System.out.println("    + " + step.getTitle() + ":");
                        System.out.println("        ID: " + step.id);
                        System.out.println("        Status: " + step.getStatus());
                    }
                }

                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    private static void handleGetIncompleteTasks(Scanner scanner) {
        try {
            List<Task> incompleteTasks = TaskService.getIncompleteTasks();

            if (incompleteTasks.isEmpty()) {
                System.out.println("No incomplete tasks found.");
                return;
            }


            for (Task task : incompleteTasks) {
                System.out.println("ID: " + task.id);
                System.out.println("Title: " + task.getTitle());
                System.out.println("Due Date: " + new SimpleDateFormat("yyyy-MM-dd").format(task.getDueDate()));
                System.out.println("Status: " + task.getStatus());
                System.out.println("Steps:");

                List<Step> steps = StepService.getStepsForTask(task.id);

                if (steps.isEmpty()) {
                    System.out.println("    No steps available for this task.");
                } else {
                    for (Step step : steps) {
                        System.out.println("    + " + step.getTitle() + ":");
                        System.out.println("        ID: " + step.id);
                        System.out.println("        Status: " + step.getStatus());
                    }
                }

                System.out.println();
            }
        } catch (EntityNotFoundException e) {
            System.out.println("Error fetching task steps: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
