package com.devsuperior.dslist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslist.dto.GameListDTO;
import com.devsuperior.dslist.entities.GameList;
import com.devsuperior.dslist.projections.GameMinProjection;
import com.devsuperior.dslist.repositories.GameListRepository;
import com.devsuperior.dslist.repositories.GameRepository;

@Service
public class GameListService {
	
	@Autowired
	private GameListRepository gameListRepository;
	
	@Autowired
	private GameRepository gameRepository;
		
	@Transactional(readOnly = true)
	public List<GameListDTO> findAll() {
		List<GameList> result = gameListRepository.findAll();
		return result.stream().map(x -> new GameListDTO(x)).toList();
	}
	
	@Transactional
	public void move(Long listId, int sourceIndex, int destinationIndex) {
		
/*para reposicionar a oredem da lista*/
		List<GameMinProjection> list = gameRepository.searchByList(listId);
		
		GameMinProjection obj = list.remove(sourceIndex);
		list.add(destinationIndex,obj);
		
/* Se o source index for menor que o destinoindex (? eh um ternario) entao retorna destinoindex*/		
		int min = sourceIndex < destinationIndex ?  sourceIndex : destinationIndex;
		int max = sourceIndex < destinationIndex ?  destinationIndex : sourceIndex;
		
/* para int i recebe min enquanto i for menor igual a max*/
			for (int i = min; i  <= max; i++) {
/* atualizar a posicao buscando o Id da lista e do game pelo getId*/
				
/* e neste caso quando e executado o resultado eh alterado entao nao eh uma*/
 /* idepodencia e usaemos a requisicao POST*/
 
			gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
		}
	}
	
	
}
