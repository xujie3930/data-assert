#!/bin/bash
export JAVA_HOME=/data/software/jdk1.8.0_151
export JRE_HOME=$JAVA_HOME/jre
export CLASSPATH=$JAVA_HOME/lib/
export PATH=$PATH:$JAVA_HOME/bin
svc=jnh-datasphere-icreditstudio-dataassert-1.3.0-SNAPSHOT-dist
codepath=/data/items/jnh-datasphere-icreditstudio-dataassert-1.3.0-SNAPSHOT-dist
RUNENV=prod

getPid() {
    psline=`ps aux | grep -i "java.*${svc}" | grep -v 'grep'`
    retval=$?
    if [ -n "$psline" ]; then
        echo -n `echo $psline | awk '{print $2}'`
    fi
    return $retval
}

export SERVER_PID=${codepath}/bin/icreditstudio-dataassert.pid

start() {
    pid=`getPid`
    if [ -n "$pid" ]; then
        echo "already running on pid $pid"
    else
        cd ${codepath}/bin
        sudo -E env PATH=$PATH nohup java -Xmx1024M -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/data -XX:+PrintGCDetails -XX:+PrintGCDateStamps -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Duser.timezone=Asia/Shanghai -cp ${codepath}/conf:${codepath}/lib/* com.hashtech.DataAssertApplication --spring.profiles.active=${RUNENV} --spring.cloud.nacos.discovery.namespace=259da15b-9025-4084-9081-a670f63e7612 >> ${codepath}/bin/${svc}.log 2>&1 &
        echo "starting on pid $!"
	echo "$!" > $SERVER_PID
    fi

}


stop() {
    pid=`getPid`
    if [ -n "$pid" ]; then
        kill $pid
        while [ -d "/proc/$pid" ]; do
            sleep 1
            echo "stopping..."
        done
    else
        echo "not running"
    fi
}


case "$2" in
    start)
        start
    ;;


    stop)
        stop
    ;;


    restart)
        stop
        sleep 1
        start
    ;;


    status)
        pid=`getPid`
        [ -n "$pid" ] && echo "running $svc on pid ${pid}" || echo stopped
    ;;

    monitor)
        pid=`getPid`
        [ -n "$pid" ] && echo "running $svc on pid ${pid}" && exit
        start
    ;;


    *)
        if [ -n "$svc" ]; then
            echo "Usage: ./$(basename $0) $svc {start|stop|restart|status|monitor}"
        else
            echo "Usage: ./$(basename $0) \$svc {start|stop|restart|status|monitor}"
        fi
    ;;
esac
