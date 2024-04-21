package ru.mirea.shchukin.employeedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import ru.mirea.shchukin.employeedb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDatabase db = App.getInstance().getDatabase();
        EmployeeDao employeeDao = db.employeeDao();
        Employee employee = new Employee();
        employee.id = 1;
        employee.name = "Halk";
        employee.power = "Мery strong";
        // запись сотрудников в базу
        employeeDao.insert(employee);
        // Загрузка всех работников
        List<Employee> employees = employeeDao.getAll();
        // Получение определенного работника с id = 1
        employee = employeeDao.getById(1);
        // Обновление полей объекта
        employee.power = "Monster";
        employeeDao.update(employee);
    }
}