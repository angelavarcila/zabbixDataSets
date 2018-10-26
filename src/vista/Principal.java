/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import control.EventsJpaController;
import control.FunctionsJpaController;
import control.HistoryJpaController;
import control.HistoryLogJpaController;
import control.HistoryStrJpaController;
import control.HistoryTextJpaController;
import control.HistoryUintJpaController;
import control.ItemsJpaController;
import control.util.GestionArchivo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Events;
import model.Functions;
import model.History;
import model.HistoryLog;
import model.HistoryStr;
import model.HistoryText;
import model.HistoryUint;
import model.Items;

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

    private static HistoryJpaController historyJpaController;
    private static HistoryLogJpaController historyLogJpaController;
    private static HistoryStrJpaController historyStrJpaController;
    private static HistoryTextJpaController historyTextJpaController;
    private static HistoryUintJpaController historyUintJpaController;

    public static void main(String[] args) {
        itemsJpaController = new ItemsJpaController();
        functionsJpaController = new FunctionsJpaController();
        eventsJpaController = new EventsJpaController();

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

                    getDataSetSNMP(host, desde, hasta);

                    break;
                case 4:
                    System.out.println("Escriba la ruta del archivo");
                    String fileName = scanner.next();
                    
                    List<String[]> matriz = gestionArchivo.getMatrixFromCSV(fileName);
                    
                    //Sacar la lista de atributos con el index como selección
                    System.out.println("---------------------");
                    for(int i = 0; i < (matriz.get(0)).length; i++){
                        System.out.println(i+" - "+matriz.get(0)[i]);
                    }
                    System.out.println("---------------------");
                    
                    //SUBMENÚ Seleccione el tipo de operación
                    System.out.println("Seleccione el tipo de operación que va a realizar");
                    System.out.println("1. + Suma");
                    System.out.println("2. % Porcentaje");
                    int operation = scanner.nextInt();
                    
                    System.out.println("Ingrese el número de las columnas con las cuales desea realizar una operación (separadas por coma)");
                    String[] clmns_ope = scanner.next().split(",");
                    int [] num_clmns_ope = new int[clmns_ope.length];
                    for(int i = 0; i < clmns_ope.length; i++){
                        num_clmns_ope[i] = Integer.valueOf(clmns_ope[i]);
                    }
                    
                    System.out.println("Ingrese el nombre de la columna que se va a crear a partir de la operación");  
                    String newcolumn = scanner.next();
                    
                    executeOperation(matriz, num_clmns_ope, operation, fileName, newcolumn);

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

            List<Functions> lista = functionsJpaController.getFunctionsByHostName(host);
            List<Events> listaE = new ArrayList<>();

            String encabezado = "1_event_id,2_event_type,3_event_clock,4_event_ns,5_event_value,6_event_ifrecovery,7_recovery_clock,"
                    + "8_recovery_ns,9_event_duration,10_function_name,11_function_parameter,12_trigger_dscr,13_trigger_expression,"
                    + "14_trigger_flags,15_trigger_priority,16_trigger_type,17_trigger_recovery_mode,18_trigger_recovery_expression,"
                    + "19_item_delay,20_item_name,21_item_type,22_item_value_type,23_item_dscr,24_item_flags,25_item_port,"
                    + "26_item_snmpcommunity,27_item_snmpoid,28_item_units";
            gestionArchivo.escrbir(encabezado, false);

            for (Functions f : lista) {
                listaE.addAll(eventsJpaController.getEventsByTriggersAndDate(f.getTriggerid().getTriggerid(), desde, hasta));
                // listaE.addAll(eventsJpaController.getEventsByTriggers(f.getTriggerid().getTriggerid()));
                for (int i = 0; i < listaE.size(); i++) {
                    //escriba.toString() = listaE.get(i).getEventid() + "," + listaE.get(i).getObjectid();
                    gestionArchivo.escrbir(getLineEventInstance(listaE.get(i), f), true);
                }
                listaE.clear();//para no repetir lineas
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

            // Armar columnas 
            for (Integer timestamp : hs) {
                gestionArchivo.escrbir(getLineSNMPInstance(listaItems, timestamp), true);
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
        escriba.append(function.getItemid().getUnits()); //unidades del item

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
    private static String getLineSNMPInstance(List<Items> items, int timestamp) {
        StringBuilder instance = new StringBuilder();
        instance.append(timestamp).append(",");

        for (Items it : items) {
            switch (it.getValueType()) {
                case 0: //float
                    Double valD = historyJpaController.getHistoryValueByItemId(it.getItemid(), timestamp);
                    if (valD != null) {
                        instance.append(valD.toString());
                    }
                    instance.append(",");
                    break;
                case 1: //Str
                    String valS = historyStrJpaController.getHistoryStrValueByItemId(it.getItemid(), timestamp);
                    if (valS != null) {
                        instance.append(valS.replaceAll("[\n\r]", " ").replaceAll(",", "-"));
                    }
                    instance.append(",");
                    break;
                case 2: //Log
                    String valL = historyLogJpaController.getHistoryLogValueByItemId(it.getItemid(), timestamp);
                    if (valL != null) {
                        instance.append(valL.replaceAll("[\n\r]", " ").replaceAll(",", "-"));
                    }
                    instance.append(",");
                    break;
                case 3: //Uint
                    Long valI = historyUintJpaController.getHistoryUintValueByItemId(it.getItemid(), timestamp);
                    if (valI != null) {
                        instance.append(valI.toString());
                    }
                    instance.append(",");
                    break;
                default: //Text
                    String valT = historyTextJpaController.getHistoryTextValueByItemId(it.getItemid(), timestamp);
                    if (valT != null) {
                        instance.append(valT.replaceAll("[\n\r]", " ").replaceAll(",", "-"));
                    }
                    instance.append(",");
                    break;
            }
        }
        instance.deleteCharAt(instance.length() - 1);//quito la última coma

        return instance.toString();
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

    /* public static void prueba (){
         String fileName = "vIOS-Core-I_DataSetSNMP.csv";

        try {
            FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis,StandardCharsets.UTF_8);
                CSVReader reader = new CSVReader(isr);
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {

                for (String e : nextLine) {
                    System.out.format("%s ", e);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

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

            File archivo = new File(pathcsv.replaceAll(".csv", "")+"_"+newcolumn+".csv");
            bw = new BufferedWriter(new FileWriter(archivo));
            gestionArchivo.leerArchivo(archivo.getName());
            gestionArchivo.escrbir(encabezado.toString(), false);
            
            if(operation==1){ //suma
                for (int i = 1; i < matriz.size(); i++) {
                    String [] row = matriz.get(i);
                    long suma = 0;
                    for (int j = 0; j < num_clmns_ope.length; j++) {
                        int indexAttr = num_clmns_ope[j];
                        //System.err.println("------>"+row[indexAttr].length());
                        //////Este if es temporal, los datos q voy a recoger despues se van a tomar al tiempo, entonces no voy a tener espacios vacíos.
                        if(!row[indexAttr].isEmpty()){
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
            }else if (operation==2){ //porcentaje
                for (int i = 1; i < matriz.size(); i++) {
                    String [] row = matriz.get(i);
                    int suma_unos = 0;
                    for (int j = 0; j < num_clmns_ope.length; j++) {
                        int indexAttr = num_clmns_ope[j];
                        //////Este if es temporal, los datos q voy a recoger despues se van a tomar al tiempo, entonces no voy a tener espacios vacíos.
                        if(!row[indexAttr].isEmpty()){
                            suma_unos = suma_unos + Integer.valueOf(row[indexAttr]);
                        }//////////////////////////////////////
                    }
                    long resultado = suma_unos / num_clmns_ope.length;
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
}
