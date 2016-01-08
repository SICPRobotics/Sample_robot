/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.wildstang.yearly.robot;

import org.wildstang.framework.auto.AutoManager;
import org.wildstang.framework.core.Core;
import org.wildstang.framework.logger.StateLogger;
import org.wildstang.framework.timer.ProfilingTimer;
import org.wildstang.hardware.crio.RoboRIOInputFactory;
import org.wildstang.hardware.crio.RoboRIOOutputFactory;

import edu.wpi.first.wpilibj.IterativeRobot;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//import edu.wpi.first.wpilibj.Watchdog;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot
{

   private StateLogger m_stateLogger = null;
   private Core m_core = null;
   private static Logger s_log = Logger.getLogger(RobotTemplate.class.getName());

   static boolean teleopPerodicCalled = false;

   private void startloggingState()
   {
      try
      {
         m_stateLogger.setWriter(new FileWriter(new File("log.txt")));
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      m_stateLogger.start();
      Thread t = new Thread(m_stateLogger);
      t.start();
   }

   /**
    * This function is run when the robot is first started up and should be used
    * for any initialization code.
    */
   public void robotInit()
   {
      m_core = new Core(RoboRIOInputFactory.class, RoboRIOOutputFactory.class);
      m_stateLogger = new StateLogger(Core.getStateTracker());

      // Load the config
      loadConfig();

      // Create application systems
      m_core.createInputs(WSInputs.values());
      m_core.createOutputs(WSOutputs.values());

      // 1. Add subsystems
      m_core.createSubsystems(WSSubsystems.values());

      // 2. Add Auto programs

      startloggingState();

      s_log.logp(Level.ALL, this.getClass().getName(), "robotInit", "Startup Completed");
   }

   private void loadConfig()
   {
      // Get config file
      File configFile = new File("/ws_config.txt");

      BufferedReader reader = null;

      try
      {
         reader = new BufferedReader(new FileReader(configFile));
         Core.getConfigManager().loadConfig(reader);
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }

      if (reader != null)
      {
         try
         {
            reader.close();
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
      }
   }

   ProfilingTimer periodTimer = new ProfilingTimer("Periodic method period", 50);
   ProfilingTimer startupTimer = new ProfilingTimer("Startup duration", 1);
   ProfilingTimer initTimer = new ProfilingTimer("Init duration", 1);

   public void disabledInit()
   {
      initTimer.startTimingSection();
      AutoManager.getInstance().clear();

      loadConfig();

      Core.getSubsystemManager().init();

      initTimer.endTimingSection();
   }

   public void disabledPeriodic()
   {
      // If we are finished with teleop, finish and close the log file
      if (teleopPerodicCalled)
      {
         m_stateLogger.stop();
      }
   }

   public void autonomousInit()
   {
      Core.getSubsystemManager().init();
      m_core.setAutoManager(AutoManager.getInstance());

      AutoManager.getInstance().startCurrentProgram();
   }

   /**
    * This function is called periodically during autonomous
    */
   public void autonomousPeriodic()
   {
      // Update all inputs, outputs and subsystems
      m_core.executeUpdate();
   }

   /**
    * This function is called periodically during operator control
    */
   public void teleopInit()
   {
      m_core.setAutoManager(null);

      Core.getSubsystemManager().init();

      periodTimer.startTimingSection();
   }

   public void teleopPeriodic()
   {
      teleopPerodicCalled = true;

      // Update all inputs, outputs and subsystems
      m_core.executeUpdate();

   }

}
