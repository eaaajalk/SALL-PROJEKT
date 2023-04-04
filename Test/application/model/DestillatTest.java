package application.model;

import application.controller.Controller;
import javafx.application.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DestillatTest {
    Destillat destillat;
    Controller controller = Controller.getTestController();
    Fad f1 = controller.createFad("001", "Borbon", 250, "Privat fad");
    Fad f2 = controller.createFad("002", "Borbon", 250, "Privat fad");
    Medarbejder m1 = controller.createMedarbejder("Oscar", "123");
    Medarbejder m2 = controller.createMedarbejder("Johan", "333");
    MaltBatch batch1 = controller.createMaltBatch("Evergreen", "001", "muld");
    MaltBatch batch2 = controller.createMaltBatch("Stairway", "002", null);
    Påfyldning påfyldning;

    @BeforeEach
    void setUp() {


    }

    // ----------------------------------------------------------------------------------
    // Test af Destillat construktor

    @Test
    void DestillatTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        assertNotNull(destillat);
    }

    @Test
    void DestillatTC2() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,20 ),
                LocalDate.of(2023, 2, 21), 80, m2, "Ny varme på 80C",
                "begravet dal under destilleriet", batch2,"002",32);

        assertNotNull(destillat);
    }

    // ----------------------------------------------------------------------------------
    // Test af ugyldig data for Destillat construktor

    @Test
    void DestillatTC5() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            destillat = controller.createDestillat(LocalDate.of(2023,1,27),
                    LocalDate.of(2023, 1, 20), 150, m1, null,
                    "begravet dal under destilleriet", batch1,"001",32);
        });

        assertEquals(exception.getMessage(),"Slut dato er mindre end start dato");
    }

    @Test
    void DestillatTC6() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                    LocalDate.of(2023, 3, 12), -10, m1, null,
                    "begravet dal under destilleriet", batch1,"001",32);
        });

        assertEquals(exception.getMessage(), "Mængde må ikke være 0 eller mindre");
    }

    @Test
    void DestillatTC12() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            destillat = controller.createDestillat(LocalDate.of(2023, 1,20 ),
                    LocalDate.of(2023, 2, 21), 80, m2, "Ny varme på 80C",
                    "begravet dal under destilleriet", batch2,"-013",40);
        });

        assertEquals(exception.getMessage(), "ID må ikke være negativ");
    }

    @Test
    void DestillatTC14() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            destillat = controller.createDestillat(LocalDate.of(2023, 1,20 ),
                    LocalDate.of(2023, 2, 21), 80, m2, "Ny varme på 80C",
                    "begravet dal under destilleriet", batch2,"002",-10);
        });

        assertEquals(exception.getMessage(), "Alkoholprocenten skal være mellem 0 til 100");
    }

    // ----------------------------------------------------------------------------------
    // Test af Destillat metode getPåfyldninger()
    @Test
    void getPåfyldingerTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
        påfyldning = destillat.createPåfyldning(10,f1,m1,destillat,
                LocalDate.of(2023, 3, 12));

        assertEquals(destillat.getPåfyldninger().get(0), påfyldning);
    }

    // ----------------------------------------------------------------------------------
    // Test af Destillat metode createPåfyldning()

    @Test
    void createPåfyldningTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
        påfyldning = destillat.createPåfyldning(50, f1, m1,
                destillat, LocalDate.of(2023, 3, 12));
        assertNotNull(påfyldning);
        assertEquals(destillat.getPåfyldninger().get(0), påfyldning);
    }

    @Test
    void createPåfyldningTC2() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
        påfyldning = destillat.createPåfyldning(40, f1, m2,
                destillat, LocalDate.of(2023, 3, 21));
        assertNotNull(påfyldning);
        assertSame(destillat.getPåfyldninger().get(0), påfyldning);
    }

    // ----------------------------------------------------------------------------------
    // Test af ugyldig data for Destillat metode createPåfyldning()
    @Test
    void createPåfyldninTC3() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            påfyldning = destillat.createPåfyldning(-10, f1, m1,
                    destillat, LocalDate.of(2023, 3, 12));
        });
        assertEquals(exception.getMessage(), "Mængden må ikke være 0 eller negativt");
    }

    @Test
    void createPåfyldningTC4() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            påfyldning = destillat.createPåfyldning(2000, f1, m1,
                    destillat, LocalDate.of(2023, 3, 12));
        });
        assertEquals(exception.getMessage(), "Mængden må ikke være større end fad størrelsen");
    }

    @Test
    void createPåfyldningTC9() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            påfyldning = destillat.createPåfyldning(40, f1, m1,
                    destillat, LocalDate.of(2023, 3, 11));
        });
        assertEquals(exception.getMessage(), "Påfyldningsdatoen må ikke fremkomme før destillat slutdatoen");
    }

    // ----------------------------------------------------------------------------------
    // Test af Destillat metode addPåfyldning()
    @Test
    void addPåfyldningTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        påfyldning = destillat.createPåfyldning(50, f1, m1,
                destillat, LocalDate.of(2023, 3, 12));

        destillat.addPåfyldning(påfyldning);
        assertTrue(destillat.getPåfyldninger().contains(påfyldning));
        assertSame(destillat.getPåfyldninger().get(0), påfyldning);
    }

    // ----------------------------------------------------------------------------------
    // Test af ugyldig data for Destillat metode addPåfyldning()
    @Test
    void addPåfyldningTC2() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
        påfyldning = destillat.createPåfyldning(40, f1, m2,
                destillat, LocalDate.of(2023, 3, 21));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            destillat.addPåfyldning(null);
        });
        assertEquals(exception.getMessage(), "Påfyldning må ikke være null");
    }

    // ----------------------------------------------------------------------------------
    // Test af Destillat metode removePåfyldning()
    @Test
    void removePåfyldningTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
        påfyldning = destillat.createPåfyldning(40, f1, m2,
                destillat, LocalDate.of(2023, 3, 21));

        destillat.removePåfyldning(påfyldning);
        assertFalse(destillat.getPåfyldninger().contains(påfyldning));
    }

    // ----------------------------------------------------------------------------------
    // Test af Destillat metode getMedarbejder()
    @Test
    void getMedarbejderTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        Medarbejder testMedarbejder = destillat.getMedarbejder();
        assertEquals(m1, testMedarbejder);
    }

    @Test
    void getMaltBatchTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);
        MaltBatch testBatch = destillat.getMaltBatch();
        assertEquals(batch1, testBatch);
    }

    @Test
    void getIDTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        String testID = destillat.getID();
        assertEquals("001", testID);
    }

    @Test
    void getSlutDatoTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        LocalDate testDato = destillat.getSlutDato();
        assertEquals(LocalDate.of(2023, 3, 12), testDato);
    }

    @Test
    void getStartDatoTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        LocalDate testDato = destillat.getStartDato();
        assertEquals(LocalDate.of(2023, 1, 27), testDato);
    }

    @Test
    void getAlkoholProcentTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        int testAlkoholProcent = destillat.getAlkoholProcent();
        assertEquals(32, testAlkoholProcent);
    }

    @Test
    void getMængdeTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        int testMængde = destillat.getMængde();
        assertEquals(150, testMængde);
    }

    @Test
    void getKommentarTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, "Privat destillat til Kristian Svendsen",
                "begravet dal under destilleriet", batch1,"001",32);

        String testKommentar = destillat.getKommentar();
        assertEquals("Privat destillat til Kristian Svendsen",testKommentar);
    }

    @Test
    void getVandTypeTC1() {
        destillat = controller.createDestillat(LocalDate.of(2023, 1,27 ),
                LocalDate.of(2023, 3, 12), 150, m1, null,
                "begravet dal under destilleriet", batch1,"001",32);

        String testVandType = destillat.getVandType();
        assertEquals("begravet dal under destilleriet", testVandType);
    }
}