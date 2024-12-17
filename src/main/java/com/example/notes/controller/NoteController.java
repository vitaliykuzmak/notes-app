
package com.example.notes.controller;

import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    //  Створення нової нотатки
    @PostMapping
    public Note createNote(@Validated @RequestBody Note note) {
        return noteRepository.save(note);
    }

    //  Отримання всіх нотаток
    @GetMapping
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    //  Отримання однієї нотатки за ID
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
        return ResponseEntity.ok(note);
    }

    //  Оновлення нотатки за ID
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @Validated @RequestBody Note updatedNote) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));

        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());

        Note savedNote = noteRepository.save(note);
        return ResponseEntity.ok(savedNote);
    }

    //  Видалення нотатки за ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));

        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}
