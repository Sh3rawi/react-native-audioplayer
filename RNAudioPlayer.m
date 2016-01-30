#import "RNAudioPlayer.h"

@implementation RNAudioPlayer

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(play:(NSString *)fileName)
{
    AVAudioSession *session = [AVAudioSession sharedInstance];
    [session setCategory: AVAudioSessionCategoryAmbient error: nil];
    [session setActive: YES error: nil];

    NSURL *soundURL = [[NSBundle mainBundle] URLForResource:[[fileName lastPathComponent] stringByDeletingPathExtension]
                                             withExtension:[fileName pathExtension]];

    if (soundURL) {
      if (self.audioPlayer.playing) {
        self.anotherPlayer = [[AVAudioPlayer alloc] initWithContentsOfURL:soundURL error:nil];
        [self.anotherPlayer play];
      } else {
        self.audioPlayer = [[AVAudioPlayer alloc] initWithContentsOfURL:soundURL error:nil];
        [self.audioPlayer play];
      }
    }
}

- (void)audioPlayerDidFinishPlaying:(AVAudioPlayer *)player successfully:(BOOL)flag
{
  if (player == self.audioPlayer) {
    self.audioPlayer.delegate = nil;
    self.audioPlayer = nil;
  } else if (player == self.anotherPlayer) {
    self.anotherPlayer.delegate = nil;
    self.anotherPlayer = nil;
  }
}

@end
