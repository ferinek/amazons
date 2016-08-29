package pl.lodz.uni.math.kslodowicz.amazons.ai;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.lodz.uni.math.kslodowicz.amazons.config.Options;
import pl.lodz.uni.math.kslodowicz.amazons.dto.TileDTO;
import pl.lodz.uni.math.kslodowicz.amazons.dto.TreeNodeDTO;
import pl.lodz.uni.math.kslodowicz.amazons.enums.AiGameResults;
import pl.lodz.uni.math.kslodowicz.amazons.service.BoardService;

@Component
public class MonteCarloAi {
	private static final String ERROR_MESSAGE = "Error while joining thread";
	Logger logger = Logger.getLogger(MonteCarloAi.class);
	@Autowired
	private BoardService board;

	@Autowired
	private Options options;
	private int player;
	Random random = new Random();

	public void moveAndShoot(int player) {
		this.player = player;

		List<TreeNodeDTO> nodes = getTreeNodes();

		int cores = Runtime.getRuntime().availableProcessors();
		Thread[] threads = new Thread[cores - 2];
		for (int i = 0; i < cores - 2; i++) {
			int number = i;
			Runnable task = () -> runSimulation(nodes, number, cores);
			threads[i] = new Thread(task);
			threads[i].start();
		}
		for (int i = 0; i < cores - 2; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				threads[i].interrupt();
				logger.error(ERROR_MESSAGE, e);
			}
		}

		TreeNodeDTO max = getMaxGameWins(nodes);

		board.move(max.getPlayerField(), max.getMove());
		board.shoot(max.getShoot());
	}

	public void runSimulation(List<TreeNodeDTO> nodes, int number, int cores) {
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis() - time < options.getMonteCarloTime()) {
			for (int i = 0; i < nodes.size(); i++) {
				if (i % (cores - 2) == number) {
					setGameResult(nodes.get(i));
				}
			}
		}
	}

	private void setGameResult(TreeNodeDTO dto) {
		switch (simulateGame(new BoardService(dto.getBoard()), (this.player % 2) + 1, 0)) {
		case DANGEROUS_LOSS:
			dto.setDangerous(true);
			break;
		case WIN:
			dto.setGameWins(dto.getGameWins() + 1);
			break;
		default:
			break;
		}

		dto.setGamePlayed(dto.getGamePlayed() + 1);
	}

	private TreeNodeDTO getMaxGameWins(List<TreeNodeDTO> nodes) {
		List<TreeNodeDTO> newNodes = getNonDangerousNodesIfPossible(nodes);
		int max = 0;
		for (int i = 1; i < newNodes.size(); i++) {
			if (newNodes.get(max).getGameWins() * 1.0
					/ newNodes.get(max).getGamePlayed() < newNodes.get(i).getGameWins() * 1.0
							/ newNodes.get(i).getGamePlayed()) {
				max = i;
			}
		}
		return newNodes.get(max);
	}

	private List<TreeNodeDTO> getNonDangerousNodesIfPossible(List<TreeNodeDTO> nodes) {
		List<TreeNodeDTO> result = new LinkedList<>();
		for (TreeNodeDTO node : nodes) {
			if (!node.isDangerous()) {
				result.add(node);
			}
		}
		if (result.isEmpty()) {
			return nodes;
		} else {
			return result;
		}
	}

	private AiGameResults simulateGame(BoardService board, int activePlayer, int count) {
		if (board.checkIfEnd(activePlayer)) {
			if (count == 1 && activePlayer == player) {
				return AiGameResults.DANGEROUS_LOSS;
			}
			return activePlayer == player ? AiGameResults.LOSS : AiGameResults.WIN;

		} else {
			List<TileDTO> playerFields = board.getPlayerFieldsWithMoves(activePlayer);
			TileDTO playerField = playerFields.get(random.nextInt(playerFields.size()));
			List<TileDTO> possibleMoves = board.getPossibleMoves(playerField);
			TileDTO possibleMove = possibleMoves.get(random.nextInt(possibleMoves.size()));
			board.move(playerField, possibleMove);
			List<TileDTO> possibleShoots = board.getPossibleMoves(possibleMove);
			TileDTO possibleShoot = possibleShoots.get(random.nextInt(possibleShoots.size()));
			board.shoot(possibleShoot);
			return simulateGame(board, (activePlayer % 2) + 1, ++count);
		}

	}

	// this method generates first level of tree
	private List<TreeNodeDTO> getTreeNodes() {
		List<TreeNodeDTO> nodes = new LinkedList<>();
		for (TileDTO playerField : board.getPlayerFieldsWithMoves(player)) {
			for (TileDTO possibleMove : board.getPossibleMoves(playerField)) {
				BoardService testBoard = new BoardService(board);
				testBoard.move(playerField, possibleMove);
				for (TileDTO shot : testBoard.getPossibleMoves(possibleMove)) {
					TreeNodeDTO node = new TreeNodeDTO();
					node.setBoard(new BoardService(testBoard));
					node.getBoard().shoot(shot);
					node.setMove(possibleMove);
					node.setPlayerField(playerField);
					node.setShoot(shot);
					nodes.add(node);
				}

			}
		}
		return nodes;
	}
}
