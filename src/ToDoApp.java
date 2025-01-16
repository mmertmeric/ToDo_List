import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ToDoApp {
    private static final String FILE_NAME = "tasks.txt";
    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        loadTasksFromFile();
        Scanner scanner =new Scanner(System.in);

        while(true){
            System.out.println("\n --- To-Do List ---");
            System.out.println("1. Görev Ekle");
            System.out.println("2. Görevleri Listele");
            System.out.println("3. Görevleri Tamamlandı Olarak İşaretle");
            System.out.println("4. Görevi Sil");
            System.out.println("5. Görevleri Tarihe Göre Sırala");
            System.out.println("6. Çıkış");
        System.out.println("Seçiminizi Yapın !");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addTask(scanner);
                break;
            case 2:
                listTasks();
                break;
            case 3:
                markTaskAsCompleted(scanner);
                break;
            case 4:
                deleteTask(scanner);
                break;
            case 5:
                sortTasksByDate();
                break;
            case 6:
                saveTasksToFile();
                System.out.println("Çıkış Yapılıyor !");
                return;
            default:
                System.out.println("Geçersiz seçim. Tekrar deneyiniz !");
            }
        }
    }

    private static void addTask(Scanner scanner) {
        System.out.println("Görev Açıklaması:");
        String description = scanner.nextLine();
        System.out.println("Son tarih (yyyy-MM-dd):");
        String dueDateString = scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(dueDateString, DateTimeFormatter.ISO_LOCAL_DATE);

        tasks.add(new Task(description, false, dueDate));
        System.out.println("Görev Başarıyla Eklendi");
    }
    private static void listTasks() {
        if (tasks.isEmpty()){
            System.out.println("Henüz görev yok !");
        } else {
            System.out.println("\n --- Görev Listesi ---");
            for (int i =0; i < tasks.size(); i++){
                System.out.println((i+1) + ". " + tasks.get(i));
            }
        }
    }

    private static void markTaskAsCompleted(Scanner scanner){
        listTasks();
        if(!tasks.isEmpty()){
            System.out.println("Tamamlandı olarak işaretlemek istediğiniz görev numarası nedir ?");
            int taskIndex = scanner.nextInt() - 1;
            if (taskIndex >= 0 && taskIndex< tasks.size()){
                tasks.get(taskIndex).setCompleted(true);
                System.out.println("Görev tamamlandı olarak işaretlendi.");
            } else {
                System.out.println("Geçersiz görev numarası. Tekrar deneyiniz !");
            }
        }
    }

    private static void deleteTask(Scanner scanner){
        listTasks();
        if(!tasks.isEmpty()){
            System.out.println("Silmek istediğiniz görev numarası nedir ?");
            int taskIndex = scanner.nextInt() - 1;
            if (taskIndex >=0 && taskIndex < tasks.size()){
                tasks.remove(taskIndex);
                System.out.println("Görev başarıyla silindi.");
            } else {
                System.out.println("Geçersiz görev numarası. Tekrar deneyiniz !");
            }
        }
    }

    private static void sortTasksByDate(){
        Collections.sort(tasks, (t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()));
        System.out.println("Görevler tarihe göre ayarlandı");
    }

    private static void saveTasksToFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.getDescription() + ";" + task.getDueDate() + ";" + task.isCompleted());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Görevler yüklenirken bir sorun oluştu. Tekrar deneyiniz !" + e.getMessage());
        }
    }

    private static void loadTasksFromFile() throws IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line =reader.readLine()) !=null) {
                String[] parts = line.split(";");
                String description = parts[0];
                LocalDate dueDate = LocalDate.parse(parts[1]);
                boolean isCompleted = Boolean.parseBoolean(parts[2]);
                tasks.add(new Task(description, isCompleted, dueDate));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Henüz bir görev dosyası yok, yeni bir dosya oluşturulacak");
        } catch (RuntimeException e) {
            System.out.println("Görevler yüklenirken bir hata oluştu. Tekrar deneyiniz !" + e.getMessage());
        }
    }
}