package com.bar.controller;

import com.bar.dto.TelegramChatMessageDTO;
import com.bar.dto.TelegramChatResponseDTO;
import com.bar.entity.UserEntity;
import com.bar.mapper.TelegramChatMessageDTOMapper;
import com.bar.repository.UserRepository;
import com.bar.service.TelegramRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.api.objects.Update;

@RestController
@RequestMapping("/api/telegram")
public class Controller {

    private final UserRepository userRepository;
    private final TelegramRequestService telegramRequestService;

    private final TelegramChatMessageDTOMapper telegramChatMessageDTOMapper;

    private static final Logger logger = LogManager.getLogger("controller-logger");


    public Controller(UserRepository userRepository, TelegramRequestService telegramRequestService, TelegramChatMessageDTOMapper telegramChatMessageDTOMapper) {
        this.userRepository = userRepository;
        this.telegramRequestService = telegramRequestService;
        this.telegramChatMessageDTOMapper = telegramChatMessageDTOMapper;
    }

    @PostMapping("/a")
    public void add() {
        userRepository.save(new UserEntity("bars", "bar", "s", "bar@gmail.com", 2L));
    }

    @PostMapping("/createAnswer")
    public ResponseEntity<TelegramChatResponseDTO> createAnswer(@RequestBody Update request) throws Exception {
        logger.info("---------------Request : " + request + " ---------------");

        TelegramChatMessageDTO requestTelegramChatMessageDTO = telegramChatMessageDTOMapper.updateToDTO(request);
        TelegramChatResponseDTO response = telegramRequestService.handleTelegramRequest(requestTelegramChatMessageDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
