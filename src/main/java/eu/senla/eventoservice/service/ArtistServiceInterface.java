package eu.senla.eventoservice.service;

import eu.senla.eventoservice.dto.ArtistModelDto;
import eu.senla.eventoservice.dto.ChangeArtistNicknameRequestDto;
import eu.senla.eventoservice.dto.DeleteArtistCardRequestDto;
import eu.senla.eventoservice.dto.RequestStatusDto;

import java.util.List;

public interface ArtistServiceInterface {

    List<ArtistModelDto> getTopThree();

    List<ArtistModelDto> getAllArtists();

    ArtistModelDto getArtistById(Long artistId);

    RequestStatusDto registerAnArtistCard(String email, ArtistModelDto artistModelDto);

    ArtistModelDto getArtistByEventIdOrTitle(String getArtistByEventIdOrTitleDto);

    RequestStatusDto deleteAnArtistCard(DeleteArtistCardRequestDto deleteArtistCardRequest);

    RequestStatusDto changeArtistNickname(ChangeArtistNicknameRequestDto changeArtistNicknameRequest);

}
