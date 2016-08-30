package pl.lodz.uni.math.kslodowicz.amazons.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import pl.lodz.uni.math.kslodowicz.amazons.dto.TileDTO;
import pl.lodz.uni.math.kslodowicz.amazons.enums.GameType;

public class BoardServiceTest {
    private BoardService objectUnderTest;

    @Before
    public void setup() {
        objectUnderTest = new BoardService();
        GameType type3x3 = GameType.TYPE_10X10;
        objectUnderTest.startFields(type3x3);
    }

    @Test
    public void startFieldsTest() {
        BoardService objectUnderTest = new BoardService();
        GameType type3x3 = GameType.TYPE_3X3;
        objectUnderTest.startFields(type3x3);

        int[][] expectedFields = { { 0, 0, 0 }, { 1, 0, 2 }, { 0, 0, 0 } };

        assertThat(expectedFields).isEqualTo(objectUnderTest.getFields());
    }

    @Test
    public void getPlayerFieldsTest() {
        TileDTO expectedField1 = new TileDTO(3, 0);
        TileDTO expectedField2 = new TileDTO(9, 3);
        TileDTO expectedField3 = new TileDTO(0, 3);
        TileDTO expectedField4 = new TileDTO(6, 0);

        assertThat((objectUnderTest.getPlayerFields(1))).containsOnlyOnce(expectedField1, expectedField2, expectedField3, expectedField4);
    }

    @Test
    public void getRemovedFields() {
        assertThat((objectUnderTest.getRemovedFields())).isEmpty();

        objectUnderTest.getFields()[0][0] = 3;
        TileDTO expectedField1 = new TileDTO(0, 0);
        assertThat((objectUnderTest.getRemovedFields())).containsOnly(expectedField1);
    }

    @Test
    public void getPlayerFieldsWithMovesTest() {
        TileDTO expectedField2 = new TileDTO(9, 3);
        TileDTO expectedField3 = new TileDTO(0, 3);
        TileDTO expectedField4 = new TileDTO(6, 0);

        objectUnderTest.getFields()[2][0] = 3;
        objectUnderTest.getFields()[4][0] = 3;
        objectUnderTest.getFields()[2][1] = 3;
        objectUnderTest.getFields()[3][1] = 3;
        objectUnderTest.getFields()[4][1] = 3;

        assertThat((objectUnderTest.getPlayerFieldsWithMoves(1))).containsOnlyOnce(expectedField2, expectedField3, expectedField4);
    }

    @Test
    public void moveTest() {
        TileDTO startField = new TileDTO(3, 0);
        TileDTO endField = new TileDTO(4, 0);

        assertThat(objectUnderTest.getPlayerFields(1)).contains(startField).doesNotContain(endField);
        objectUnderTest.move(startField, endField);

        assertThat(objectUnderTest.getPlayerFields(1)).contains(endField).doesNotContain(startField);
    }

    @Test
    public void shootTest() {
        TileDTO shootField = new TileDTO(4, 0);

        assertThat(objectUnderTest.getRemovedFields()).doesNotContain(shootField);
        objectUnderTest.shoot(shootField);

        assertThat(objectUnderTest.getRemovedFields()).contains(shootField);
    }

    @Test
    public void checkIfEndTestFalse() {
        assertThat(objectUnderTest.checkIfEnd(1)).isEqualTo(false);
    }

    @Test
    public void checkIfEndTestTrue() {
        objectUnderTest = new BoardService();
        GameType type3x3 = GameType.TYPE_3X3;
        objectUnderTest.startFields(type3x3);

        int[][] testField = { { 1, 3, 0 }, { 3, 3, 0 }, { 0, 0, 2 } };
        objectUnderTest.setFields(testField);

        assertThat(objectUnderTest.checkIfEnd(1)).isEqualTo(true);
    }

    @Test
    public void toDatabaseStringTest() {
        assertThat(objectUnderTest.toDatabaseString())
                .isEqualTo("0001002000000000000000000000001000000002000000000000000000001000000002000000000000000000000001002000");
    }

    @Test
    public void fromDatabaseStringTest() {
        BoardService locaclObjectUnderTest = new BoardService();
        locaclObjectUnderTest.fromDatabaseString("0001002000000000000000000000001000000002000000000000000000001000000002000000000000000000000001002000");
        assertThat(locaclObjectUnderTest.getFields()).isEqualTo(objectUnderTest.getFields());
        assertThat(locaclObjectUnderTest.getSize()).isEqualTo(objectUnderTest.getSize());
    }
    
    @Test
    public void getTurnTest(){
        assertThat(objectUnderTest.getTurn()).isEqualTo(0);
        int[][] testField = { { 1, 3, 0 }, { 3, 3, 0 }, { 0, 0, 2 } };
        
        objectUnderTest.setFields(testField);
        objectUnderTest.setSize(3);
        
        assertThat(objectUnderTest.getTurn()).isEqualTo(3);
    }
    

}
