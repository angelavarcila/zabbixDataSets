/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import control.EventRecoveryJpaController;
import control.EventsJpaController;
import control.FunctionsJpaController;
import control.HistoryJpaController;
import control.HistoryLogJpaController;
import control.HistoryStrJpaController;
import control.HistoryTextJpaController;
import control.HistoryUintJpaController;
import control.ItemsJpaController;
import control.TriggersJpaController;
import control.util.GestionArchivo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.EventRecovery;
import model.Events;
import model.Functions;
import model.History;
import model.HistoryLog;
import model.HistoryStr;
import model.HistoryText;
import model.HistoryUint;
import model.Items;
import model.Triggers;
import org.eclipse.persistence.jpa.jpql.parser.ElseExpressionBNF;

/**
 *
 * @author ove
 */
public class Principal {

    private static Scanner scanner;
    //private static final String ARCHIVO = "resultado.txt";
    private static GestionArchivo gestionArchivo;
    //private static String[] datos;

    private static ItemsJpaController itemsJpaController;
    private static FunctionsJpaController functionsJpaController;
    private static EventsJpaController eventsJpaController;
    private static EventRecoveryJpaController eventRecoveryJpaController;
    private static TriggersJpaController triggersJpaController;

    private static HistoryJpaController historyJpaController;
    private static HistoryLogJpaController historyLogJpaController;
    private static HistoryStrJpaController historyStrJpaController;
    private static HistoryTextJpaController historyTextJpaController;
    private static HistoryUintJpaController historyUintJpaController;

    public static void main(String[] args) {
        itemsJpaController = new ItemsJpaController();
        functionsJpaController = new FunctionsJpaController();
        eventsJpaController = new EventsJpaController();
        eventRecoveryJpaController = new EventRecoveryJpaController();
        triggersJpaController = new TriggersJpaController();

        historyJpaController = new HistoryJpaController();
        historyLogJpaController = new HistoryLogJpaController();
        historyStrJpaController = new HistoryStrJpaController();
        historyTextJpaController = new HistoryTextJpaController();
        historyUintJpaController = new HistoryUintJpaController();

        gestionArchivo = new GestionArchivo();
        scanner = new Scanner(System.in);
        //  gestionArchivo.leerArchivo(ARCHIVO);

        String host;
        String fdesde; //fecha desde
        String hdesde; //hora desde
        String fhasta; //fecha hasta
        String hhasta; //hora hasta
        String fileName;
        int desde;
        int hasta;

        Timestamp dtimestamp;
        Timestamp htimestamp;

        int opc;
        do {
            System.out.println("Elija una opción:");
            System.out.println("1. Eventos por host");
            System.out.println("2. Histórico de ítems por host");
            System.out.println("3. Generar data set (sólo información SNMP)");
            System.out.println("4. Operaciones con columnas");
            System.out.println("5. Generar etiqueta");
            System.out.println("9. Salir");
            opc = scanner.nextInt();

            switch (opc) {
                case 1:
                    System.out.println("Escriba el nombre del host");
                    host = scanner.next();

                    System.out.println("Fecha Desde: yyyy-MM-dd");
                    fdesde = scanner.next();
                    System.out.println("Hora Desde: hh:mm:ss");
                    hdesde = scanner.next();
                    dtimestamp = Timestamp.valueOf(fdesde + " " + hdesde);

                    System.out.println("Fecha Hasta: yyyy-MM-dd");
                    fhasta = scanner.next();
                    System.out.println("Hora Hasta: formato: hh:mm:ss");
                    hhasta = scanner.next();
                    htimestamp = Timestamp.valueOf(fhasta + " " + hhasta);

                    desde = Integer.parseInt(String.valueOf(dtimestamp.getTime()).substring(0, 10));
                    hasta = Integer.parseInt(String.valueOf(htimestamp.getTime()).substring(0, 10));

                    getFunctionsPorHost(host, desde, hasta);
                    break;
                case 2:
                    System.out.println("Escriba el nombre del host");
                    host = scanner.next();

                    System.out.println("Fecha Desde: yyyy-MM-dd");
                    fdesde = scanner.next();
                    System.out.println("Hora Desde: hh:mm:ss");
                    hdesde = scanner.next();
                    dtimestamp = Timestamp.valueOf(fdesde + " " + hdesde);

                    System.out.println("Fecha Hasta: yyyy-MM-dd");
                    fhasta = scanner.next();
                    System.out.println("Hora Hasta: formato: hh:mm:ss");
                    hhasta = scanner.next();
                    htimestamp = Timestamp.valueOf(fhasta + " " + hhasta);

                    desde = Integer.parseInt(String.valueOf(dtimestamp.getTime()).substring(0, 10));
                    hasta = Integer.parseInt(String.valueOf(htimestamp.getTime()).substring(0, 10));

                    getHistoryByHost(host, desde, hasta);

                    break;
                case 3:
                    System.out.println("Escriba el nombre del host");
                    host = scanner.next();

                    System.out.println("Fecha Desde: yyyy-MM-dd");
                    fdesde = scanner.next();
                    System.out.println("Hora Desde: hh:mm:ss");
                    hdesde = scanner.next();
                    dtimestamp = Timestamp.valueOf(fdesde + " " + hdesde);

                    System.out.println("Fecha Hasta: yyyy-MM-dd");
                    fhasta = scanner.next();
                    System.out.println("Hora Hasta: formato: hh:mm:ss");
                    hhasta = scanner.next();
                    htimestamp = Timestamp.valueOf(fhasta + " " + hhasta);

                    desde = Integer.parseInt(String.valueOf(dtimestamp.getTime()).substring(0, 10));
                    hasta = Integer.parseInt(String.valueOf(htimestamp.getTime()).substring(0, 10));

                    System.out.println("Agrupación de atributos por minuto común");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    int agrup = scanner.nextInt();

                    if (agrup == 2) {
                        getDataSetSNMP(host, desde, hasta);
                    } else {
                        Timestamp dtimestamp_agr = Timestamp.valueOf(fdesde + " " + hdesde.substring(0, 6) + "00");//inicio desde el segundo 1
                        Timestamp htimestamp_agr = Timestamp.valueOf(fhasta + " " + hhasta.substring(0, 6) + "00");
                        int desde_agr = Integer.parseInt(String.valueOf(dtimestamp_agr.getTime()).substring(0, 10));
                        int hasta_agr = Integer.parseInt(String.valueOf(htimestamp_agr.getTime()).substring(0, 10));

                        getDataSetSNMPagrup(host, desde_agr, hasta_agr);
                    }

                    break;
                case 4:
                    System.out.println("Escriba la ruta del conjunto de datos");
                    fileName = scanner.next();

                    List<String[]> matriz = gestionArchivo.getMatrixFromCSV(fileName);

                    //Sacar la lista de atributos con el index como selección
                    System.out.println("---------------------");
                    for (int i = 0; i < (matriz.get(0)).length; i++) {
                        System.out.println(i + " - " + matriz.get(0)[i]);
                    }
                    System.out.println("---------------------");

                    //SUBMENÚ Seleccione el tipo de operación
                    /*System.out.println("Seleccione el tipo de operación que va a realizar");
                    System.out.println("1. + Suma");
                    System.out.println("2. % Porcentaje");
                    int operation = scanner.nextInt();

                    System.out.println("Ingrese el número de las columnas con las cuales desea realizar una operación (separadas por coma)");
                    String[] clmns_ope = scanner.next().split(",");
                    int[] num_clmns_ope = new int[clmns_ope.length];
                    for (int i = 0; i < clmns_ope.length; i++) {
                        num_clmns_ope[i] = Integer.valueOf(clmns_ope[i]);
                    }

                    System.out.println("Ingrese el nombre de la columna que se va a crear a partir de la operación");
                    String newcolumn = scanner.next();*/
                    System.out.println("Escriba la ruta del archivo que contiene la lista de operaciones que va a aplicar");
                    String fileOperations = scanner.next();
                    List<String[]> matrizOperations = gestionArchivo.getMatrixFromCSV(fileOperations);

                    for (String[] operation : matrizOperations) {
                        System.out.println("operation->"+operation[operation.length-1]);
                        int[] num_clmns_ope = new int[operation.length-2];//menos 2 porque hay dos campos que indican la operacioón a realizar y el nombre de la nueva columna
                        for (int i = 0; i < operation.length-2; i++) {
                            num_clmns_ope[i] = Integer.valueOf(operation[i+1]);
                        }
                        executeOperation(matriz, num_clmns_ope, Integer.valueOf(operation[0]), fileName, operation[operation.length-1]);
                        matriz = gestionArchivo.getMatrixFromCSV(fileName); //actualizo la matriz
                    }
                    //executeOperation(matriz, num_clmns_ope, operation, fileName, newcolumn);

                    break;
                case 5:
                    //GENERAR ETIQUETA
                    System.out.println("Escriba la ruta del archivo CSV");
                    fileName = scanner.next();

                    System.out.println("Escriba los nombres de los elementos de red internos (separados por coma)");
                    String[] eleRedInt = scanner.next().split(",");

                    System.out.println("Fecha Desde: yyyy-MM-dd");
                    fdesde = scanner.next();
                    System.out.println("Hora Desde: hh:mm:ss");
                    hdesde = scanner.next();
                    dtimestamp = Timestamp.valueOf(fdesde + " " + hdesde);

                    System.out.println("Fecha Hasta: yyyy-MM-dd");
                    fhasta = scanner.next();
                    System.out.println("Hora Hasta: formato: hh:mm:ss");
                    hhasta = scanner.next();
                    htimestamp = Timestamp.valueOf(fhasta + " " + hhasta);

                    //SUBMENÚ Seleccione el tipo de operación
                    System.out.println("Qué tipo de etiqueta desea: (* indica las etiquetas q permiten aplicar algoritmos)");
                    System.out.println("1. *Mayor Severidad (indica la severidad del evento)");
                    System.out.println("2. Lista de severidades (se indica todas las severidades de los eventos clasificados en el mismo instante)");
                    System.out.println("3. *Niveles + Mayor Severidad (indica el nivel donde se produjo el evento y la severidad)");
                    System.out.println("4. *Nivel cercano + Severidad");
                    System.out.println("5. *Mayor severidad indicando nivel");
                    System.out.println("6. BySelf");
                    System.out.println("7. Por defecto (EleReN-eventId1|...|EleRedN-eventIdN)");

                    int label = scanner.nextInt();

                    // buscar los eventos de cada uno de los elmentos de red internos
                    desde = Integer.parseInt(String.valueOf(dtimestamp.getTime()).substring(0, 10));
                    hasta = Integer.parseInt(String.valueOf(htimestamp.getTime()).substring(0, 10));

                    setClassesLabel(fileName, eleRedInt, desde, hasta, label);

                    break;
                case 6:
                    //TODO agrupar los archivos que contienen los flujos
                    String filesPath = "/home/angela/nProbe";

                    //leer la fecha de inicio y fin (año/mes/día)
                    System.out.println("Fecha Desde: yyyy/MM/dd");
                    fdesde = scanner.next();

                    System.out.println("Fecha Hasta: yyyy/MM/dd");
                    fhasta = scanner.next();
                    break;
                case 9:
                    System.out.println("ADIOS!!!");
                    break;
                default:
                    System.out.println("\n Elija nuevamente \n");
                    break;
            }
        } while (opc != 9);

    }

    /**
     * Obtiene funciones por el nombre de host que se escribe como parámetro
     *
     * @param host el nombre del host
     * @param desde fecha inicial para consultar
     * @param hasta fecha final para consultar
     */
    public static void getFunctionsPorHost(String host, int desde, int hasta) {
        BufferedWriter bw = null;
        try {
            //Crear archivo que tendrá los resultados:
            File archivo = new File(host + "_Events.csv");
            bw = new BufferedWriter(new FileWriter(archivo));
            //gestionArchivo = new GestionArchivo();
            gestionArchivo.leerArchivo(archivo.getName());

            //HashSet<Functions> lista = new HashSet();
            //lista.addAll(functionsJpaController.getFunctionsByHostName(host));
            List<Functions> lista = functionsJpaController.getFunctionsByHostName(host);
            List<Events> listaE = new ArrayList<>();

            String encabezado = "1_event_id,2_event_type,3_event_clock,4_event_ns,5_event_value,6_event_ifrecovery,recovery_id,7_recovery_clock,"
                    + "8_recovery_ns,9_event_duration,10_function_name,11_function_parameter,12_trigger_dscr,13_trigger_expression,"
                    + "14_trigger_flags,15_trigger_priority,16_trigger_type,17_trigger_recovery_mode,18_trigger_recovery_expression,"
                    + "19_item_delay,20_item_name,21_item_type,22_item_value_type,23_item_dscr,24_item_flags,25_item_port,"
                    + "26_item_snmpcommunity,27_item_snmpoid,28_item_units,29_item_history_value";
            gestionArchivo.escrbir(encabezado, false);
            for (Functions f : lista) {
                if (!f.getTriggerid().getDescription().contains("Interface Null0")) { //Para ignorar los eventos disparados por interfaces nulas             
                    listaE.addAll(eventsJpaController.getEventsByTriggersAndDate(f.getTriggerid().getTriggerid(), desde, hasta));
                    // listaE.addAll(eventsJpaController.getEventsByTriggers(f.getTriggerid().getTriggerid()));
                    for (int i = 0; i < listaE.size(); i++) {
                        //escriba.toString() = listaE.get(i).getEventid() + "," + listaE.get(i).getObjectid();
                        //SÓLO ESCRIBIR LA INSTANCIA SI EL EVENTO NO ES DE RECUPERACIÓN
                        List<EventRecovery> er = eventRecoveryJpaController.getEventRecoveryById(listaE.get(i));
                        if (er == null || er.isEmpty()) {
                            gestionArchivo.escrbir(getLineEventInstance(listaE.get(i), f), true);
                        }
                    }
                    listaE.clear();//para no repetir lineas 
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param host
     * @param desde
     * @param hasta
     */
    public static void getHistoryByHost(String host, int desde, int hasta) {
        BufferedWriter bw = null;
        try {
            //gestionArchivo = new GestionArchivo();

            String encabezado = "1_history_clock,2_history_ns,3_history_value,3_item_id,19_item_delay,20_item_name,21_item_type,"
                    + "22_item_value_type,23_item_dscr,24_item_flags,25_item_port,26_item_snmpcommunity,27_item_snmpoid,28_item_units";

            List<Items> lista = itemsJpaController.getItemsByHostName(host);

            //BUSQUEDA EN HISTORY
            File archivo = new File(host + "_HistoryFloat.csv");
            bw = new BufferedWriter(new FileWriter(archivo));
            gestionArchivo.leerArchivo(archivo.getName());
            gestionArchivo.escrbir(encabezado, false);
            List<History> listaH = new ArrayList<>();
            for (Items it : lista) {
                //listaH.addAll(historyJpaController.getHistoryByItemId(it.getItemid()));
                listaH.addAll(historyJpaController.getHistoryByItemIdAndDate(it.getItemid(), desde, hasta));
                for (int i = 0; i < listaH.size(); i++) {
                    gestionArchivo.escrbir(getLineHistoryInstance(listaH.get(i), it), true);
                }
                listaH.clear();//para no repetir lineas
            }

            //BUSQUEDA EN HISTORY_UINT
            File archivoUint = new File(host + "_HistoryUint.csv");
            bw = new BufferedWriter(new FileWriter(archivoUint));
            gestionArchivo.leerArchivo(archivoUint.getName());
            gestionArchivo.escrbir(encabezado, false);
            List<HistoryUint> listaUint = new ArrayList<>();
            for (Items it : lista) {
                listaUint.addAll(historyUintJpaController.getHistoryUintByItemIdAndDate(it.getItemid(), desde, hasta));

                for (int i = 0; i < listaUint.size(); i++) {
                    gestionArchivo.escrbir(getLineHistoryInstance(listaUint.get(i), it), true);
                }
                listaUint.clear();//para no repetir lineas
            }

            //BUSQUEDA EN HISTORY_LOG
            File archivoLog = new File(host + "_HistoryLog.csv");
            bw = new BufferedWriter(new FileWriter(archivoLog));
            gestionArchivo.leerArchivo(archivoLog.getName());
            gestionArchivo.escrbir(encabezado, false);
            List<HistoryLog> listaLog = new ArrayList<>();
            for (Items it : lista) {
                //listaLog.addAll(historyLogJpaController.getHistoryLogByItemId(it.getItemid()));
                listaLog.addAll(historyLogJpaController.getHistoryLogByItemIdAndDate(it.getItemid(), desde, hasta));
                for (int i = 0; i < listaLog.size(); i++) {
                    gestionArchivo.escrbir(getLineHistoryInstance(listaLog.get(i), it), true);
                }
                listaLog.clear();//para no repetir lineas
            }

            //BUSQUEDA EN HISTORY_TEXT
            File archivoText = new File(host + "_HistoryText.csv");
            bw = new BufferedWriter(new FileWriter(archivoText));
            gestionArchivo.leerArchivo(archivoText.getName());
            gestionArchivo.escrbir(encabezado, false);
            List<HistoryText> listaText = new ArrayList<>();
            for (Items it : lista) {
                //listaText.addAll(historyTextJpaController.getHistoryTextByItemId(it.getItemid()));
                listaText.addAll(historyTextJpaController.getHistoryTextByItemIdAndDate(it.getItemid(), desde, hasta));
                for (int i = 0; i < listaText.size(); i++) {
                    gestionArchivo.escrbir(getLineHistoryInstance(listaText.get(i), it), true);
                }
                listaText.clear();//para no repetir lineas
            }

            //BUSQUEDA EN HISTORY_STR
            File archivoStr = new File(host + "_HistoryStr.csv");
            bw = new BufferedWriter(new FileWriter(archivoStr));
            gestionArchivo.leerArchivo(archivoStr.getName());
            gestionArchivo.escrbir(encabezado, false);
            List<HistoryStr> listaStr = new ArrayList<>();
            for (Items it : lista) {
                //listaStr.addAll(historyStrJpaController.getHistoryStrByItemId(it.getItemid()));
                listaStr.addAll(historyStrJpaController.getHistoryStrByItemIdAndDate(it.getItemid(), desde, hasta));
                for (int i = 0; i < listaStr.size(); i++) {
                    gestionArchivo.escrbir(getLineHistoryInstance(listaStr.get(i), it), true);
                }
                listaStr.clear();//para no repetir lineas
            }
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param host
     * @param desde
     * @param hasta
     */
    private static void getDataSetSNMP(String host, int desde, int hasta) {
        BufferedWriter bw = null;
        try {
            List<Items> listaItems = itemsJpaController.getItemsByHostName(host);

            StringBuilder encabezado = new StringBuilder("timestamp,");
            for (Items item : listaItems) {
                encabezado.append(item.getName().replace(" ", "_")).append(",");
            }
            encabezado.deleteCharAt(encabezado.length() - 1);//quito la última coma

            File archivo = new File(host + "_DataSetSNMP.csv");
            bw = new BufferedWriter(new FileWriter(archivo));
            gestionArchivo.leerArchivo(archivo.getName());
            gestionArchivo.escrbir(encabezado.toString(), false);

            //Crear la lista de todos los timestamp  de los ítems del host, sin repetir
            HashSet<Integer> hs = new HashSet();
            for (Items it : listaItems) {
                switch (it.getValueType()) {
                    case 0: //float
                        List<History> listaH = new ArrayList<>();
                        listaH.addAll(historyJpaController.getHistoryByItemIdAndDate(it.getItemid(), desde, hasta));
                        for (int i = 0; i < listaH.size(); i++) {
                            hs.add(listaH.get(i).getHistoryPK().getClock());
                        }
                        break;
                    case 1: //Str
                        List<HistoryStr> listaS = new ArrayList<>();
                        listaS.addAll(historyStrJpaController.getHistoryStrByItemIdAndDate(it.getItemid(), desde, hasta));
                        for (int i = 0; i < listaS.size(); i++) {
                            hs.add(listaS.get(i).getHistoryStrPK().getClock());
                        }
                        break;
                    case 2: //Log
                        List<HistoryLog> listaL = new ArrayList<>();
                        listaL.addAll(historyLogJpaController.getHistoryLogByItemIdAndDate(it.getItemid(), desde, hasta));
                        for (int i = 0; i < listaL.size(); i++) {
                            hs.add(listaL.get(i).getHistoryLogPK().getClock());
                        }
                        break;
                    case 3: //Uint
                        List<HistoryUint> listaU = new ArrayList<>();
                        listaU.addAll(historyUintJpaController.getHistoryUintByItemIdAndDate(it.getItemid(), desde, hasta));
                        for (int i = 0; i < listaU.size(); i++) {
                            hs.add(listaU.get(i).getHistoryUintPK().getClock());
                        }
                        break;
                    default: //Text
                        List<HistoryText> listaT = new ArrayList<>();
                        listaT.addAll(historyTextJpaController.getHistoryTextByItemIdAndDate(it.getItemid(), desde, hasta));
                        for (int i = 0; i < listaT.size(); i++) {
                            hs.add(listaT.get(i).getHistoryTextPK().getClock());
                        }
                        break;
                }
            }

            // convert to TreeSet para que la lista quede ordenada
            TreeSet<Integer> tsHT = new TreeSet<Integer>(hs);

            // Armar columnas 
            //for (Integer timestamp : hs) {
            for (Integer timestamp : tsHT) {
                gestionArchivo.escrbir(getLineSNMPInstance(listaItems, timestamp, false), true);
            }
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void getDataSetSNMPagrup(String host, int desde, int hasta) {
        try {
            List<Items> listaItems = itemsJpaController.getItemsByHostName(host);

            StringBuilder encabezado = new StringBuilder("timestamp,");
            for (Items item : listaItems) {
                encabezado.append(item.getName().replace(" ", "_")).append(",");
            }
            encabezado.deleteCharAt(encabezado.length() - 1);//quito la última coma

            File archivo = new File(host + "_DataSetSNMP.csv");

            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

            gestionArchivo.leerArchivo(archivo.getName());
            gestionArchivo.escrbir(encabezado.toString(), false);

            //long siguiente = desde + 60; //primer rango de búsqueda
            //hacer un while que haga las búsquedas mientras desde sea menor o igual que hasta
            while (desde <= hasta) {
                //System.out.println("desde "+desde+" - hasta "+hasta);
                gestionArchivo.escrbir(getLineSNMPInstance(listaItems, desde, true), true);
                desde = desde + 60;
            }

        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param event
     * @param function
     * @return
     */
    public static String getLineEventInstance(Events event, Functions function) {
        StringBuilder escriba = new StringBuilder();

        //INFORMACIÓN DEL EVENTO
        escriba.append(event.getEventid()).append(","); //id del evento
        escriba.append(event.getSource()).append(","); //tipo de evento
        //escriba.append(event.getObjectid() + ","); //id del trigger NO NECESARIO
        escriba.append(event.getClock()).append(","); // momento en el que el evento fue creado (timestamp)
        escriba.append(event.getNs()).append(","); // momento en el que el evento fue creado (en nanosegundos)
        escriba.append(event.getValue()).append(","); // estado (indica si es un problema o no)

        //INFORMACIÓN DE RECUPERACIÓN
        if (event.getEventRecovery() != null) {
            escriba.append("1,"); // 1 indica que es un evento que se recuperó
            escriba.append(event.getEventRecovery().getREventid().getEventid()).append(","); // indica el id del evento de recuperación
            escriba.append(event.getEventRecovery().getREventid().getClock()).append(","); // momento en el que el evento fue recuperado (timestamp)
            escriba.append(event.getEventRecovery().getREventid().getNs()).append(","); // momento en el que el evento fue recuperado (en nanosegundos)

            int interval = event.getEventRecovery().getREventid().getClock() - event.getClock();
            escriba.append(interval).append(","); //duración en timestamp        
        } else {
            escriba.append("0,0,0,0,"); // 0 indica que el evento no se recuperó, el tiempo de recuperación es 0
        }

        //INFORMACIÓN DE LA FUNCIÓN
        escriba.append(function.getFunction()).append(","); //nombre de la función
        escriba.append(function.getParameter()).append(","); //parámetro de la función

        //INFORMACIÓN DEL TRIGGER
        escriba.append(function.getTriggerid().getDescription()).append(","); // descripción del trigger
        escriba.append(function.getTriggerid().getExpression().replaceAll("[\n\r]", " ")).append(","); // expresión del trigger
        escriba.append(function.getTriggerid().getFlags()).append(","); //indica el origen del trigger
        escriba.append(function.getTriggerid().getPriority()).append(","); // veveridad del trigger
        escriba.append(function.getTriggerid().getType()).append(","); // indica si el trigger puede generar o no múltiples problemas
        escriba.append(function.getTriggerid().getRecoveryMode()).append(","); //modo de generación del evento OK
        escriba.append(function.getTriggerid().getRecoveryExpression().replaceAll("[\n\r]", " ")).append(","); //expresión de recuperación

        //INFORMACIÓN DEL ITEM
        escriba.append(function.getItemid().getDelay()).append(","); // intervalo de tiempo de actualización
        escriba.append(function.getItemid().getName()).append(","); //nombre del ítem
        escriba.append(function.getItemid().getType()).append(","); //tipo de ítem
        escriba.append(function.getItemid().getValueType()).append(","); //tipo de información del ítem
        escriba.append(function.getItemid().getDescription().replaceAll("[\n\r]", " ").replaceAll(",", "")).append(","); // descripción del ítem
        escriba.append(function.getItemid().getFlags()).append(","); //origen del ítem
        escriba.append(function.getItemid().getPort()).append(","); //puerto monitorizado por el ítem
        escriba.append(function.getItemid().getSnmpCommunity()).append(","); //comunidad snmp
        escriba.append(function.getItemid().getSnmpOid()).append(","); //oid snmp
        escriba.append(function.getItemid().getUnits()).append(","); //unidades del item

        //VALOR DEL ÍTEM EN EL TIEMPO CLOCK
        switch (function.getItemid().getValueType()) {
            case 0: //float
                History h = historyJpaController.getHistoryByItemIdAndClock(function.getItemid().getItemid(), event.getClock());
                if (h != null) {
                    escriba.append(h.getValue());
                } else {
                    escriba.append("?");
                }
                break;
            case 1: //Str
                HistoryStr hstr = historyStrJpaController.getHistoryStrByItemIdAndClock(function.getItemid().getItemid(), event.getClock());
                if (hstr != null) {
                    escriba.append(hstr.getValue());
                } else {
                    escriba.append("?");
                }
                break;
            case 2: //Log
                HistoryLog hlog = historyLogJpaController.getHistoryLogByItemIdAndClock(function.getItemid().getItemid(), event.getClock());
                if (hlog != null) {
                    escriba.append(hlog.getValue());
                } else {
                    escriba.append("?");
                }
                break;
            case 3: //Uint
                HistoryUint huint = historyUintJpaController.getHistoryUintByItemIdAndClock(function.getItemid().getItemid(), event.getClock());
                if (huint != null) {
                    escriba.append(huint.getValue());
                } else {
                    escriba.append("?");
                }
                break;
            default: //Text
                HistoryText htext = historyTextJpaController.getHistoryTextByItemIdAndClock(function.getItemid().getItemid(), event.getClock());
                if (htext != null) {
                    escriba.append(htext.getValue());
                } else {
                    escriba.append("?");
                }
                break;
        }

        return escriba.toString();
    }

    /**
     *
     * @param history
     * @param i
     * @return
     */
    public static String getLineHistoryInstance(Object history, Items i) {
        StringBuilder escriba = new StringBuilder();

        //VALOR DEL HISTÓRICO
        if (history.getClass().getName().equalsIgnoreCase("model.HistoryLog")) {
            escriba.append(((HistoryLog) history).getHistoryLogPK().getClock()).append(","); //momento en el que el valor fue recibido
            escriba.append(((HistoryLog) history).getNs()).append(","); //momento en el que el valor fue recibido, en nanosegundos
            escriba.append(((HistoryLog) history).getValue()).append(","); //valor del ítem.
        } else if (history.getClass().getName().equalsIgnoreCase("model.HistoryStr")) {
            escriba.append(((HistoryStr) history).getHistoryStrPK().getClock()).append(","); //momento en el que el valor fue recibido
            escriba.append(((HistoryStr) history).getNs()).append(","); //momento en el que el valor fue recibido, en nanosegundos
            escriba.append(((HistoryStr) history).getValue().replaceAll("[\n\r]", " ").replaceAll(",", "-")).append(","); //valor del ítem.
        } else if (history.getClass().getName().equalsIgnoreCase("model.HistoryText")) {
            escriba.append(((HistoryText) history).getHistoryTextPK().getClock()).append(","); //momento en el que el valor fue recibido
            escriba.append(((HistoryText) history).getNs()).append(","); //momento en el que el valor fue recibido, en nanosegundos
            escriba.append(((HistoryText) history).getValue()).append(","); //valor del ítem.
        } else if (history.getClass().getName().equalsIgnoreCase("model.HistoryUint")) {
            escriba.append(((HistoryUint) history).getHistoryUintPK().getClock()).append(","); //momento en el que el valor fue recibido
            escriba.append(((HistoryUint) history).getNs()).append(","); //momento en el que el valor fue recibido, en nanosegundos
            escriba.append(((HistoryUint) history).getValue()).append(","); //valor del ítem.
        } else {
            escriba.append(((History) history).getHistoryPK().getClock()).append(","); //momento en el que el valor fue recibido
            escriba.append(((History) history).getNs()).append(","); //momento en el que el valor fue recibido, en nanosegundos
            escriba.append(((History) history).getValue()).append(","); //valor del ítem.
        }

        //DATOS DEL ITEM
        escriba.append(i.getItemid()).append(","); // item id
        escriba.append(i.getDelay()).append(","); // intervalo de tiempo de actualización
        escriba.append(i.getName()).append(","); //nombre del ítem
        escriba.append(i.getType()).append(","); //tipo de ítem
        escriba.append(i.getValueType()).append(","); //tipo de información del ítem
        escriba.append(i.getDescription().replaceAll("[\n\r]", " ").replaceAll(",", "")).append(","); // descripción del ítem
        escriba.append(i.getFlags()).append(","); //origen del ítem
        escriba.append(i.getPort()).append(","); //puerto monitorizado por el ítem
        escriba.append(i.getSnmpCommunity()).append(","); //comunidad snmp
        escriba.append(i.getSnmpOid()).append(","); //oid snmp
        escriba.append(i.getUnits()); //unidades del item

        return escriba.toString();
    }

    /**
     *
     * @param it
     * @param timestamp
     * @return
     */
    private static String getLineSNMPInstance(List<Items> items, int timestamp, boolean byRange) {
        StringBuilder instance = new StringBuilder();
        instance.append(timestamp).append(",");
        int measuredItems = 0;
        for (Items it : items) {
            switch (it.getValueType()) {
                case 0: //float
                    Double valD = null;
                    if (byRange) {
                        valD = historyJpaController.getHistoryValueByItemIdAndRange(it.getItemid(), timestamp, timestamp + 59);
                    } else {
                        valD = historyJpaController.getHistoryValueByItemId(it.getItemid(), timestamp);
                    }

                    if (valD != null) {
                        instance.append(valD.toString());
                        measuredItems = measuredItems + 1;
                    } else {
                        instance.append("-1");
                    }
                    instance.append(",");
                    break;
                case 1: //Str
                    String valS = null;
                    if (byRange) {
                        valS = historyStrJpaController.getHistoryStrValueByItemIdAndRange(it.getItemid(), timestamp, timestamp + 59);
                    } else {
                        valS = historyStrJpaController.getHistoryStrValueByItemId(it.getItemid(), timestamp);
                    }

                    if (valS != null) {
                        instance.append(valS.replaceAll("[\n\r]", " ").replaceAll(",", "-"));
                        measuredItems = measuredItems + 1;
                    } else {
                        instance.append("-1");
                    }
                    instance.append(",");
                    break;
                case 2: //Log
                    String valL = null;
                    if (byRange) {
                        valL = historyLogJpaController.getHistoryLogValueByItemIdAndRange(it.getItemid(), timestamp, timestamp + 59);
                    } else {
                        valL = historyLogJpaController.getHistoryLogValueByItemId(it.getItemid(), timestamp);
                    }

                    if (valL != null) {
                        instance.append(valL.replaceAll("[\n\r]", " ").replaceAll(",", "-"));
                        measuredItems = measuredItems + 1;
                    } else {
                        instance.append("-1");
                    }
                    instance.append(",");
                    break;
                case 3: //Uint
                    Long valI = null;
                    if (byRange) {
                        valI = historyUintJpaController.getHistoryUintValueByItemIdAndRange(it.getItemid(), timestamp, timestamp + 59);
                    } else {
                        valI = historyUintJpaController.getHistoryUintValueByItemId(it.getItemid(), timestamp);
                    }

                    if (valI != null) {
                        instance.append(valI.toString());
                        measuredItems = measuredItems + 1;
                    } else {
                        instance.append("-1");
                    }
                    instance.append(",");
                    break;
                default: //Text
                    String valT = null;
                    if (byRange) {
                        valT = historyTextJpaController.getHistoryTextValueByItemIdAndRange(it.getItemid(), timestamp, timestamp + 59);
                    } else {
                        valT = historyTextJpaController.getHistoryTextValueByItemId(it.getItemid(), timestamp);
                    }

                    if (valT != null) {
                        instance.append(valT.replaceAll("[\n\r]", " ").replaceAll(",", "-"));
                        measuredItems = measuredItems + 1;
                    } else {
                        instance.append("-1");
                    }
                    instance.append(",");
                    break;
            }
        }
        instance.deleteCharAt(instance.length() - 1);//quito la última coma

        if (measuredItems != 0) {
            return instance.toString();
        } else {
            return "";
        }

        // return instance.toString();
    }

    /**
     * Obtiene los datos del archivo para su lectura de acuerdo a los parámetros
     *
     * @param lectura es el dato que se obtiene del método leerArchivo de la
     * clase GestionArchivo
     * @param numeroColumnas el número de columnas que tendrá el documento a
     * escribir
     *
     * @return la lista de cadenas de texto con el contenido del archivo que se
     * lee
     */
    public static List<String> obtenerDatosArchivo(String[] lectura, int numeroColumnas) {
        String textoArchivo = lectura[0];
        Integer tamanioArchivo = Integer.parseInt(lectura[1]);
        String[] datosPorLinea = textoArchivo.split("\n");

        List<String> lista = new ArrayList<>();
        for (int i = 0; i < tamanioArchivo; i++) {
            String[] texto = datosPorLinea[i].split(",");
            for (int j = 0; j < numeroColumnas; j++) {
                lista.add(texto[j]);
            }
        }
        return lista;
    }

    /**
     *
     * @param matriz
     * @param num_clmns_ope
     * @param operation
     * @param pathcsv
     */
    private static void executeOperation(List<String[]> matriz, int[] num_clmns_ope, int operation, String pathcsv, String newcolumn) {
        BufferedWriter bw = null;
        try {
            StringBuilder encabezado = new StringBuilder();
            for (int i = 0; i < matriz.get(0).length; i++) {
                encabezado.append(matriz.get(0)[i]).append(",");
            }
            encabezado.append(newcolumn);

            //File archivo = new File(pathcsv.replaceAll(".csv", "") + "_" + newcolumn + ".csv");
            File archivo = new File(pathcsv);

            bw = new BufferedWriter(new FileWriter(archivo));
            gestionArchivo.leerArchivo(archivo.getAbsolutePath());//archivo.getName());
            gestionArchivo.escrbir(encabezado.toString(), false);

            if (operation == 1) { //suma
                for (int i = 1; i < matriz.size(); i++) {
                    String[] row = matriz.get(i);
                    long suma = 0;
                    for (int j = 0; j < num_clmns_ope.length; j++) {
                        int indexAttr = num_clmns_ope[j];
                        //System.err.println("------>"+row[indexAttr].length());
                        //////Este if es temporal, los datos q voy a recoger despues se van a tomar al tiempo, entonces no voy a tener espacios vacíos.
                        if (!row[indexAttr].isEmpty()) {
                            suma = suma + Long.valueOf(row[indexAttr]);
                        }
                        ////////////////////////////////////
                    }
                    //imprimo instancia resultante en archivo
                    StringBuilder instancia = new StringBuilder();
                    for (int j = 0; j < row.length; j++) {
                        instancia.append(row[j]).append(",");
                    }
                    instancia.append(suma);
                    gestionArchivo.escrbir(instancia.toString(), true);
                }
            } else if (operation == 2) { //porcentaje
                for (int i = 1; i < matriz.size(); i++) {
                    String[] row = matriz.get(i);
                    float suma_unos = 0;
                    for (int j = 0; j < num_clmns_ope.length; j++) {
                        int indexAttr = num_clmns_ope[j];
                        if (!row[indexAttr].isEmpty() && Integer.valueOf(row[indexAttr]) != -1) { //se suma si el valor del estado operativo no es vacío ni -1
                            suma_unos = suma_unos + Float.valueOf(row[indexAttr]);
                        }//////////////////////////////////////
                    }
                    float resultado = suma_unos / num_clmns_ope.length;
                    
                    System.out.println("porcentaje->"+suma_unos+" / "+num_clmns_ope.length+" = "+resultado);
                    //imprimo instancia resultante en archivo
                    StringBuilder instancia = new StringBuilder();
                    for (int j = 0; j < row.length; j++) {
                        instancia.append(row[j]).append(",");
                    }
                    instancia.append(resultado);
                    gestionArchivo.escrbir(instancia.toString(), true);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Crea las etiquetas para el conjunto de datos SNMP basándose en los
     * eventos de la lista de elementos de red y el archivo correspondiente al
     * data set
     *
     * @param fileName
     * @param eleRedInt
     * @param desde
     * @param hasta
     */
    private static void setClassesLabel(String fileName, String[] eleRedInt, int desde, int hasta, int label) {
        BufferedWriter bw = null;
        try {
            //data set del dispositivo periférico sin etiqueta
            List<String[]> matrizPerif = gestionArchivo.getMatrixFromCSV(fileName);
            int numAttr = matrizPerif.get(0).length;
            for (int i = 0; i < eleRedInt.length; i++) {
                //List<Events> get event list by host
                List<Events> eventIntLst = getEventsByHost(eleRedInt[i], desde, hasta);
                System.out.println("tamaño eventos: " + eventIntLst.size());
                //List<String[]> newMatrizPerif = new ArrayList();
                for (Events event : eventIntLst) {
                    //Pasar el siguiente for a un método
                    for (int j = 1; j < matrizPerif.size(); j++) {//desde 1 para no leer el encabezado
                        int tPerif = Integer.valueOf(matrizPerif.get(j)[0]);
                        int tPerifAnterior = 0;
                        if (j != 1) {
                            tPerifAnterior = Integer.valueOf(matrizPerif.get(j - 1)[0]);
                        }
                        if (label != 6) {//Código chambón, arreglarlo mñas bonito

                            if (tPerifAnterior != 0 && tPerifAnterior < event.getClock() && tPerif > event.getClock()) {
                                //matrizPerif.get(j-1) CLASIFICA EVENTO
                                List<String> instancia = new ArrayList<String>(Arrays.asList(matrizPerif.get(j - 1)));
                                if (instancia.size() == numAttr) {
                                    //Debo agregar la clasee como nuevo String
                                    instancia.add(createLabel("", eleRedInt[i], event, label));//eleRedInt[i] + "-" + event.getEventid());
                                    //System.err.println(event.getEventid()+"-"+j+"--clasifico evento-->"+tPerifAnterior+"-"+event.getClock()+"-"+tPerif);
                                } else {
                                    //System.err.println("--REclasifico evento--");
                                    String classs = instancia.get(instancia.size() - 1);
                                    instancia.remove(instancia.size() - 1);
                                    instancia.add(createLabel(classs, eleRedInt[i], event, label));//classs + "|"+eleRedInt[i] + "-" + event.getEventid());//LEE PEGO EL OTRO EVENTO
                                }
                                //remplazar la fila de la matriz por la nueva instancia
                                String[] nuevoArray = new String[instancia.size()];
                                //Aquí convertimos la lista a arreglo nuevamente
                                nuevoArray = instancia.toArray(nuevoArray);
                                matrizPerif.set(j - 1, nuevoArray); //en caso de q salga error, lo hago con iterator
                                break;
                            }
                        } else {//Codigo chambón
                            if (tPerifAnterior != 0 && (tPerif == event.getClock() || (tPerifAnterior < event.getClock() && tPerif > event.getClock()))) {
                                //matrizPerif.get(j-1) CLASIFICA EVENTO
                                List<String> instancia = new ArrayList<String>(Arrays.asList(matrizPerif.get(j - 1)));
                                if (instancia.size() == numAttr) {
                                    //Debo agregar la clasee como nuevo String
                                    instancia.add(createLabel("", eleRedInt[i], event, 5));//eleRedInt[i] + "-" + event.getEventid());
                                    //System.err.println(event.getEventid()+"-"+j+"--clasifico evento-->"+tPerifAnterior+"-"+event.getClock()+"-"+tPerif);
                                } else {
                                    //System.err.println("--REclasifico evento--");
                                    String classs = instancia.get(instancia.size() - 1);
                                    instancia.remove(instancia.size() - 1);
                                    instancia.add(createLabel(classs, eleRedInt[i], event, 5));//classs + "|"+eleRedInt[i] + "-" + event.getEventid());//LEE PEGO EL OTRO EVENTO
                                }
                                //remplazar la fila de la matriz por la nueva instancia
                                String[] nuevoArray = new String[instancia.size()];
                                //Aquí convertimos la lista a arreglo nuevamente
                                nuevoArray = instancia.toArray(nuevoArray);
                                matrizPerif.set(j - 1, nuevoArray); //en caso de q salga error, lo hago con iterator
                                break;
                            }
                        }
                    }

                }
            }

            //imprimir la matriz en un archivo      
            File archivo = new File(fileName.replaceAll(".csv", "") + "_Labeled.csv");
            bw = new BufferedWriter(new FileWriter(archivo));

            gestionArchivo.leerArchivo(archivo.getAbsolutePath());

            StringBuilder headLine = new StringBuilder();
            for (int i = 0; i < matrizPerif.get(0).length; i++) {
                headLine.append(matrizPerif.get(0)[i]).append(",");
            }
            headLine.append("class");
            gestionArchivo.escrbir(headLine.toString(), false);

            for (int i = 1; i < matrizPerif.size(); i++) {
                StringBuilder instanceLine = new StringBuilder();
                //for (int j = 0; j < matrizPerif.get(i).length; i++) {
                for (String attribute : matrizPerif.get(i)) {
                    instanceLine.append(attribute).append(",");
                }
                if (matrizPerif.get(0).length == matrizPerif.get(i).length) {//no tiene etiqueta, entonces pongo la coma

                    switch (label) {
                        case 1: //Mayor Severidad 
                            instanceLine.append("0,");
                            break;
                        case 3: //Niveles + Mayor Severidad
                            //ETIQUETA; DxAy x->mayor severidad del nivel distribución, y->mayor severidad del nivel de acceso
                            instanceLine.append("D0A0,");
                            break;
                        case 4: //3. Nivel cercano + Mayor severidad del nivel más cercano
                            //ETIQUETA: Xx, X->D nivel distribución, A nivel acceso; x-> mayor severidad del nivel
                            instanceLine.append("NE,");
                            break;
                        case 5: //Mayor severidad y nivel
                            instanceLine.append("NE,");
                            break;
                        case 6: //Mayor severidad y nivel
                            instanceLine.append("NE,");
                            break;
                        default: // Elem-eventdID|...
                            instanceLine.append(",");
                            break;
                    }

                }
                gestionArchivo.escrbir(instanceLine.deleteCharAt(instanceLine.length() - 1).toString(), true);
            }

        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Obtiene la lista de eventos de un elemento de red por su nombre y un
     * rango de fecha
     *
     * @param eleRedInt
     * @param desde
     * @param hasta
     * @return
     */
    private static List<Events> getEventsByHost(String eleRedInt, int desde, int hasta) {
        List<Functions> lista = functionsJpaController.getFunctionsByHostName(eleRedInt);
        List<Events> listaE = new ArrayList<>();

        for (Functions f : lista) {
            listaE.addAll(eventsJpaController.getEventsByTriggersAndDate(f.getTriggerid().getTriggerid(), desde, hasta));
        }

        Collections.sort(listaE, (x, y) -> Integer.valueOf(x.getClock()).compareTo(y.getClock()));
        /* Collections.sort(listaE, new Comparator<Events>() {
            @Override
            public int compare(Events e1, Events e2) {
                return Integer.valueOf(e1.getClock()).compareTo(Integer.valueOf(e2.getClock()));
            }
        });*/

        return listaE;
    }

    private static String createLabel(String classs, String eleRedInt, Events event, int labelType) {
        StringBuilder label = new StringBuilder();
        Triggers trigger = triggersJpaController.getTriggersById(event.getObjectid()).get(0);

        switch (labelType) {
            case 1: //Mayor Severidad 
                if (classs != null && !classs.isEmpty()) {
                    int old_classs = Integer.valueOf(classs);
                    if (trigger.getPriority() >= old_classs) {
                        label.append(trigger.getPriority());
                    } else {
                        label.append(old_classs);
                    }
                } else {
                    label.append(trigger.getPriority());
                }
                break;
            case 2: //Lista de severidades              
                label.append(classs).append("|").append(trigger.getPriority());
                break;
            case 3: //Niveles + Mayor Severidad
                //ETIQUETA; DxAy x->mayor severidad del nivel distribución, y->mayor severidad del nivel de acceso
                if (eleRedInt.contains("Dis")) { //es nivel de distribución
                    if (classs != null && !classs.isEmpty()) {//ya tenía una clasificación antes
                        int old_classs = Integer.valueOf(classs.substring(classs.indexOf("D") + 1, classs.indexOf("D") + 2));//.charAt(classs.indexOf("D") + 1));
                        if (trigger.getPriority() >= old_classs) {
                            label.append("D").append(trigger.getPriority()).append(classs.substring(classs.indexOf("A"), classs.length()));
                        } else {
                            label.append(classs); //no se cambia la etiqueta q tenía
                        }
                    } else {//primera vez q se crea la etiqueta
                        label.append("D").append(trigger.getPriority()).append("A0");
                    }
                } else { // es nivel de acceso
                    if (classs != null && !classs.isEmpty()) {//ya tenía una clasificación antes
                        int old_classs = Integer.valueOf(classs.substring(classs.indexOf("A") + 1, classs.indexOf("A") + 2));
                        if (trigger.getPriority() >= old_classs) {
                            label.append(classs.substring(classs.indexOf("D"), classs.indexOf("A") + 1)).append(trigger.getPriority());
                        } else {
                            label.append(classs); //no se cambia la etiqueta q tenía
                        }
                    } else {//primera vez q se crea la etiqueta
                        label.append("D0A").append(trigger.getPriority());
                    }
                }
                break;
            case 4: //3. Nivel cercano + Mayor severidad del nivel más cercano
                //ETIQUETA: Xx, X->D nivel distribución, A nivel acceso; x-> mayor severidad del nivel
                if (eleRedInt.contains("Dis")) { //es nivel de distribución
                    if (classs != null && !classs.isEmpty()) {
                        int old_classs = Integer.valueOf(classs.substring(1, 2));
                        if (trigger.getPriority() >= old_classs) {
                            label.append("D").append(trigger.getPriority());
                        } else {
                            label.append(classs);
                        }
                    } else {
                        label.append("D").append(trigger.getPriority());
                    }
                } else if (classs.contains("D")) { // es nivel de acceso pero ya existe una etiqueta de un nivel superior
                    label.append(classs);
                } else { // es nivel de acceso y no se ha etiquetado
                    if (classs != null && !classs.isEmpty()) {
                        int old_classs = Integer.valueOf(classs.substring(1, 2));
                        if (trigger.getPriority() >= old_classs) {
                            label.append("A").append(trigger.getPriority());
                        } else {
                            label.append(classs);
                        }
                    } else {
                        label.append("A").append(trigger.getPriority());
                    }
                }
                break;
            case 5: //Mayor severidad y nivel
                String level = "A";
                if (eleRedInt.contains("Dis")) {
                    level = "D";
                }

                if (classs != null && !classs.isEmpty()) {
                    int old_classs = Integer.valueOf(classs.substring(1, 2));
                    if (trigger.getPriority() > old_classs) {
                        label.append(level).append(trigger.getPriority());
                    } else if (trigger.getPriority() == old_classs
                            && classs.substring(0, 1).equalsIgnoreCase("A")
                            && level.equalsIgnoreCase("D")) {
                        label.append(level).append(trigger.getPriority());
                    } else {
                        label.append(classs);
                    }
                } else {
                    label.append(level).append(trigger.getPriority());
                }
                break;
            default: // Elem-eventdID|...
                label.append(classs).append("|").append(eleRedInt).append("-").append(event.getEventid());
                break;
        }

        return label.toString();
    }
}
