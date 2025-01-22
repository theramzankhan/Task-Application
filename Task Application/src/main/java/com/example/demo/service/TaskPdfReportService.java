package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class TaskPdfReportService {
	
	@Autowired
	private TaskRepository taskRepository;

	// Helper method to generate PDF
	public ByteArrayOutputStream generatedPdfReport(List<Task> tasks) throws DocumentException, IOException {
		Document document = new Document();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, byteArrayOutputStream);
		
		document.open();
		document.add(new Paragraph("Task Report"));
		document.add(new Paragraph(" "));
		
		//Table to display tasks
		PdfPTable table = new PdfPTable(5); // 5 columns: ID, Title, Assigned User, Status, Due Date
		table.addCell("Task ID");
		table.addCell("Title");
		table.addCell("Assigned User");
		table.addCell("Status");
		table.addCell("Due Date");
		
		for(Task task : tasks) {
			table.addCell(String.valueOf(task.getId()));
			table.addCell(task.getTitle());
			table.addCell(task.getUser() != null ? task.getUser().getName() : "No User");
			table.addCell(task.getStatus() != null ? task.getStatus().toString() : "No Status");
			table.addCell(task.getDueDate() != null ? task.getDueDate().toString() : "No Due Date");
		}
		
		document.add(table);
		document.close();
		
		return byteArrayOutputStream;
	}
	
	// Generate PDF for all tasks
	public ByteArrayOutputStream getAllTasksPdfReport() throws DocumentException, IOException {
		List<Task> tasks = taskRepository.findAll(); //fetch all tasks
		return generatedPdfReport(tasks);
	}
}
