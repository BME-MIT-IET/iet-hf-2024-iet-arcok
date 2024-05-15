#/bin/bash
NEW_DISPLAY=42
DONE="no"

mkdir -p ~/.vnc
PASSWORD=$(openssl rand -base64 12)

while [ "$DONE" == "no" ]
do
  out=$(xdpyinfo -display :${NEW_DISPLAY} 2>&1)
  if [[ "$out" == name* ]] || [[ "$out" == Invalid* ]]
  then
    # command succeeded; or failed with access error;  display exists
    (( NEW_DISPLAY+=1 ))
  else
    # display doesn't exist
    DONE="yes"
  fi
done

echo "Using first available display :${NEW_DISPLAY}"

OLD_DISPLAY=${DISPLAY}
vncserver ":${NEW_DISPLAY}" -localhost -geometry 1600x1200 -depth 16
export DISPLAY=:${NEW_DISPLAY}

echo "$PASSWORD" | x11vnc -storepasswd -input - | sudo tee ~/.vnc/passwd >/dev/null 2>&1

"$@"

export DISPLAY=${OLD_DISPLAY}
vncserver -kill ":${NEW_DISPLAY}"
