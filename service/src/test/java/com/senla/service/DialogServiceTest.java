package com.senla.service;

import com.senla.entity.Dialog;
import com.senla.repository.DialogRepository;
import com.senla.service.dialog.DialogService;
import com.senla.service.dialog.DialogServiceImpl;
import com.senla.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@EnableAutoConfiguration
public class DialogServiceTest {

    private DialogService dialogService;

    @Mock
    private DialogRepository dialogRepository;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        dialogService = new DialogServiceImpl(dialogRepository, userService);
    }

    private final Dialog dialog = new Dialog(1L, "Test dialog", LocalDateTime.now());
    private final Long dialogId = 1L;

    @Test
    public void getDialogTest() {
        when(dialogRepository.findById(dialog.getId())).thenReturn(Optional.of(dialog));
        Dialog returnDialog = dialogService.getDialog(dialog.getId());
        Assertions.assertEquals(dialog, returnDialog);
    }

    @Test
    public void updateDialogTest() {
        given(dialogRepository.save(dialog)).willReturn(dialog);
        Dialog expected = dialogService.updateDialog(dialog);
        assertThat(expected).isNotNull();
        verify(dialogRepository).save(any(Dialog.class));
    }

    @Test
    public void shouldReturnFindAll() {
        List<Dialog> dialogs = new ArrayList();
        dialogs.add(new Dialog(dialogId, "Test dialog 1", LocalDateTime.now()));
        dialogs.add(new Dialog(dialogId, "Test dialog 2", LocalDateTime.now()));
        dialogs.add(new Dialog(dialogId, "Test dialog 3", LocalDateTime.now()));
        given(dialogRepository.findAll()).willReturn(dialogs);
        List<Dialog> expected = dialogService.getAllDialogs();
        assertEquals(expected, dialogs);
    }

    @Test
    public void getDialogByNameTest() {
        when(dialogRepository.getDialogByName(dialog.getName())).thenReturn(dialog);
        Dialog resultDialog = dialogService.getDialogByName(dialog.getName());
        Assertions.assertEquals(dialog, resultDialog);
    }

    @Test
    public void addUserToDialogTest() {
        dialogService.updateDialog(dialog);
        verify(dialogRepository, times(1)).save(dialog);
    }

    @Test
    public void shouldBeDelete() {
        when(dialogRepository.findById(dialog.getId())).thenReturn(Optional.of(dialog));
        dialogService.deleteDialog(dialogId);
        verify(dialogRepository, times(1)).deleteById(dialog.getId());
    }
}
